package ec.edu.udla.ui.regions.arduinointegration;

import ec.edu.udla.arduino.comunicacion.ComunicadorPuertoSerial;
import ec.edu.udla.ui.MainApp;
import ec.edu.udla.ui.regions.AbstractController;
import ec.edu.udla.ui.regions.login.LoginController;
import ec.edu.udla.ui.regions.sms.SmsController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.kordamp.bootstrapfx.scene.layout.Panel;

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
    private Button botonConectar;

    @FXML
    private TextField comando;

    private Stage dialogStage;

    private Label label;

    private boolean cerrar = false;

    @FXML
    private void enviar() throws Exception {
        ComunicadorPuertoSerial.obtenerInstancia().enviarCadenaDeTexto(comando.getText());
        comando.setText("");
    }

    @FXML
    private void conectar() {
        try {

            ComunicadorPuertoSerial.obtenerInstancia().configurarConexion(puertosSeriales.getValue().toString());
            botonConectar.setDisable(true);
            container.mostrarBarraDeMenu();

            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("Loading.fxml"));
            Parent node = loader.load();
            label = FXUtils.getChildByID(node, "mensaje");
            label.setText("Estableciendo conexion con dispositivo arduino");

            dialogStage = new Stage();
            dialogStage.setTitle("Procesando");
            dialogStage.initModality(Modality.NONE);
            dialogStage.initOwner(container.getPrimaryStage());
            Scene scene = new Scene(node, 500, 150);
            scene.getStylesheets().add("bootstrapfx.css");
            scene.getStylesheets().addAll(MainApp.class.getResource("style.css").toExternalForm());
            dialogStage.setScene(scene);
            dialogStage.initStyle(StageStyle.DECORATED);
            dialogStage.show();

            ComunicadorPuertoSerial.obtenerInstancia().addObservers(this);
        } catch (Exception e) {
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
    }

    @Override
    public void update(Observable o, Object texto) {
        if (texto != null) {
            if (texto.toString().contains("Fin")) {
                label.setText("que fue");
//                dialogStage.close();
            }
        }
    }
}
