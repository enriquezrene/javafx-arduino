package ec.edu.udla.ui.regions.sms;

import ec.edu.udla.arduino.comunicacion.ComunicadorPuertoSerial;
import ec.edu.udla.arduino.sms.domain.Sms;
import ec.edu.udla.arduino.sms.service.ServicioEnvioSms;
import ec.edu.udla.ui.regions.AbstractController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SmsController extends AbstractController{

    @FXML
    private TextField destinatatio;

    @FXML
    private TextArea contenido;

    public void enviarSms(ActionEvent actionEvent) {
        try {
            Sms sms = new Sms.Builder().conContenido(contenido.getText()).conDestinatario(destinatatio.getText()).build();
            new ServicioEnvioSms().enviarMensaje(sms, ComunicadorPuertoSerial.obtenerInstancia());
        }catch (Exception e){
            e.printStackTrace();
        }
        Stage stage = (Stage) destinatatio.getScene().getWindow();
        stage.close();
    }
}