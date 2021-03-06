package ec.edu.udla.ui.regions.arduino;

import java.net.URL;
import java.util.ResourceBundle;

import ec.edu.udla.arduino.comunicacion.ComunicadorPuertoSerial;
import ec.edu.udla.ui.regions.AbstractController;
import ec.edu.udla.ui.regions.DrawableRegion;
import ec.edu.udla.ui.regions.RegionsContainer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class LeerInformacionArduinoController extends AbstractController implements Initializable {

    @FXML
    private ComboBox<String> puertosSeriales;

    @FXML
    private TextArea textoArduino;

    @FXML
    private Button botonConectar;

    @FXML
    private TextField comando;

    @FXML
    private void enviar() throws Exception {
        ComunicadorPuertoSerial.obtenerInstancia().enviarCadenaDeTexto(comando.getText());
        comando.setText("");
    }

    @FXML
    private void conectar() throws Exception {
        ComunicadorPuertoSerial.obtenerInstancia().configurarConexion(puertosSeriales.getValue().toString());
        botonConectar.setDisable(true);
    }

    @FXML
    private void cerrar() throws Exception {
        ComunicadorPuertoSerial.obtenerInstancia().cerrarConexion();
        botonConectar.setDisable(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String[] puertosDisponibles = ComunicadorPuertoSerial.obtenerInstancia().obtenerPuertosSerialesDisponibles();
        for (int i = 0; i < puertosDisponibles.length; i++) {
            puertosSeriales.getItems().add(puertosDisponibles[i]);
        }
        ComunicadorPuertoSerial.obtenerInstancia().asignarCampoParaEscribirSalida(textoArduino);
    }

}
