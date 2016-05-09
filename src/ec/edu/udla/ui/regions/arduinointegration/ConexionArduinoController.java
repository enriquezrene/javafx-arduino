package ec.edu.udla.ui.regions.arduinointegration;

import ec.edu.udla.arduino.comunicacion.ComunicadorPuertoSerial;
import ec.edu.udla.arduino.parser.LecturaGlucometroArduino;
import ec.edu.udla.domain.LecturaGlucometro;
import ec.edu.udla.domain.dao.PacienteDao;
import ec.edu.udla.ui.MainApp;
import ec.edu.udla.ui.regions.AbstractController;
import ec.edu.udla.ui.regions.login.LoginController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;
import java.util.ResourceBundle;

public class ConexionArduinoController extends AbstractController implements Initializable, Observer {

    @FXML
    private ComboBox<String> puertosSeriales;

    @FXML
    private TextArea textoArduino;

    @FXML
    private TextField comando;

    private Stage dialogStage;

    private Label label;

    @FXML
    private Button btnLeerInformacion, botonConectar;

    @FXML
    private ImageView loading;

    private PacienteDao pacienteDao;

    private int indiceDelRegistroProcesado = 1;

    @FXML
    private void conectar() {
        try {
            ComunicadorPuertoSerial.obtenerInstancia().configurarConexion(puertosSeriales.getValue().toString());
            container.mostrarBarraDeMenu();
            loading.setVisible(true);
            ComunicadorPuertoSerial.obtenerInstancia().addObservers(this);

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = LoginController.buildDialog(Alert.AlertType.ERROR, "No se puede establecer la conexion, intente mas tarde", null);
            alert.show();
        }
    }


    @FXML
    private void cerrarConexion() throws Exception {
        ComunicadorPuertoSerial.obtenerInstancia().cerrarConexion();
        this.indiceDelRegistroProcesado = 1;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String[] puertosDisponibles = ComunicadorPuertoSerial.obtenerInstancia().obtenerPuertosSerialesDisponibles();
        for (int i = 0; i < puertosDisponibles.length; i++) {
            puertosSeriales.getItems().add(puertosDisponibles[i]);
        }
        textoArduino.setDisable(true);
        ComunicadorPuertoSerial.obtenerInstancia().asignarCampoParaEscribirSalida(textoArduino);
        loading.setVisible(false);
        pacienteDao = new PacienteDao();
    }

    @Override
    public void update(Observable o, Object texto) {
        if (texto != null) {
            System.out.println(texto);
            if (texto.toString().contains("Fin")) {
                loading.setVisible(false);
            } else if (!texto.toString().contains("Inicializa")) {
                loading.setVisible(false);
                if (texto.toString().trim().length() < 25) {
                    Platform.runLater(() -> {
                        Alert dialog = LoginController.buildDialog(Alert.AlertType.CONFIRMATION, "Toda la informacion ha sido procesada exitosamente, desea intentar reprocesar?", "Informacion");
                        dialog.getDialogPane().getChildren().stream().filter(node -> node instanceof Label).forEach(node -> ((Label) node).setMinHeight(Region.USE_PREF_SIZE));
                        Optional<ButtonType> result = dialog.showAndWait();
                        ComunicadorPuertoSerial.obtenerInstancia().removeObservers(this);
                        if (result.get() == ButtonType.OK) {
                            System.out.println("REPROCESAR");
                            try {
                                ComunicadorPuertoSerial.obtenerInstancia().cerrarConexion();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            System.out.println("NO REPROCESAR");
                            limpiarInformacionDelArduino();
                            ponerArduinoEnModoOnline();
                            container.setCurrentScreen(MainApp.PANTALLA_DATOS_PACIENTE);
                        }
                    });
                } else {
                    try {
                        LecturaGlucometro lecturaGlucometro = extraerInformacion(texto.toString().trim().substring(20).trim());
                        pacienteDao.registrarLectura(lecturaGlucometro);
                        Platform.runLater(() -> {
                            Notifications.create().title("Informacion guardada").text("La información ha sido registrada exitosamente").showInformation();
                        });
                    } catch (Exception e) {
                        System.out.println("Error al procesar informacion");
                        e.printStackTrace();
//                        Platform.runLater(() -> {
//                            Notifications.create().title("Error").text("Notifique este error al administrador " + e.getMessage()).showWarning();
//                        });
                    }
                }
            }
        }
    }

    private void ponerArduinoEnModoOnline() {
        ComunicadorPuertoSerial.obtenerInstancia().enviarCadenaDeTexto("A");
    }

    private void limpiarInformacionDelArduino() {
        ComunicadorPuertoSerial.obtenerInstancia().enviarCadenaDeTexto("C");
    }

    private LecturaGlucometro extraerInformacion(String cadenaArduino) {
        return new LecturaGlucometroArduino(cadenaArduino).obtenerObjeto();

    }

    private void desactivarBotones(Button... buttons) {
        for (int j = 0; j < buttons.length; j++) {
            buttons[j].setDisable(true);
        }
    }

    public void leerInformacion(ActionEvent event) throws InterruptedException {
        loading.setVisible(true);
        ComunicadorPuertoSerial.obtenerInstancia().enviarCadenaDeTexto(indiceDelRegistroProcesado++ + "");
    }


}
