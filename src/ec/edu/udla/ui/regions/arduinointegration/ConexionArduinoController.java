package ec.edu.udla.ui.regions.arduinointegration;

import ec.edu.udla.arduino.comunicacion.ComunicadorPuertoSerial;
import ec.edu.udla.arduino.parser.LecturaGlucometroArduino;
import ec.edu.udla.domain.LecturaGlucometro;
import ec.edu.udla.ui.regions.AbstractController;
import ec.edu.udla.ui.regions.login.LoginController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;
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
    private Button btnLeerInformacion, botonConectar, btnActivarModoOnLine, btnLimpiarArduino;

    @FXML
    private ImageView loading;

    private int i = 1;

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
    private void cerrar() throws Exception {
        ComunicadorPuertoSerial.obtenerInstancia().cerrarConexion();

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
//        desactivarBotones(btnLeerInformacion, botonConectar, btnActivarModoOnLine, btnLimpiarArduino);
        botonConectar.setDisable(false);
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
                    System.out.println("Mensaje vacio: "+texto);
                }else{
                    extraerInformacion(texto.toString().trim().substring(20).trim());
                }
            }
        }
    }

    private void extraerInformacion(String cadenaArduino) {
        System.out.println("extraer info de: "+cadenaArduino);
        LecturaGlucometro lecturaGlucometro = new LecturaGlucometroArduino(cadenaArduino).obtenerObjeto();

    }

    private void desactivarBotones(Button... buttons){
        for (int j = 0; j < buttons.length; j++) {
            buttons[j].setDisable(true);
        }
    }

    public void leerInformacion(ActionEvent event) throws InterruptedException {
        loading.setVisible(true);
        ComunicadorPuertoSerial.obtenerInstancia().enviarCadenaDeTexto(i++ + "");
    }

    public void limpiarArduino(ActionEvent event) {
        loading.setVisible(true);
        ComunicadorPuertoSerial.obtenerInstancia().enviarCadenaDeTexto("C");
    }

    public void activarModoOnLine(ActionEvent event) {
        loading.setVisible(true);
        ComunicadorPuertoSerial.obtenerInstancia().enviarCadenaDeTexto("A");
    }

    public void activarModoOffLine(ActionEvent event) {
        loading.setVisible(true);
        ComunicadorPuertoSerial.obtenerInstancia().enviarCadenaDeTexto("B");
    }
}
