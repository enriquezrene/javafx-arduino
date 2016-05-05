package ec.edu.udla.ui.regions.sms;

import ec.edu.udla.arduino.comunicacion.ComunicadorPuertoSerial;
import ec.edu.udla.arduino.sms.domain.Sms;
import ec.edu.udla.arduino.sms.service.ServicioEnvioSms;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * Created by moe on 5/4/16.
 */
public class SmsController {

    @FXML
    private TextField destinatatio;

    @FXML
    private TextArea contenido;

    public void enviarSms(ActionEvent actionEvent) {
        Sms sms = new Sms.Builder().conContenido(contenido.getText()).conDestinatario(destinatatio.getText()).build();
        new ServicioEnvioSms().enviarMensaje(sms, ComunicadorPuertoSerial.obtenerInstancia());
    }
}
