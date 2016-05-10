package ec.edu.udla.arduino.sms.service;

import ec.edu.udla.arduino.comunicacion.ComunicadorPuertoSerial;
import ec.edu.udla.arduino.sms.domain.Sms;

import java.util.Observable;
import java.util.Observer;

public class ServicioEnvioSms implements Observer {

    public static final String NOTIFICACION_LUEGO_COMANDO_ARDUINO = "celular";
    public static final String NOTIFICACION_LUEGO_DESTINATARIO = "caracteres";
    public static final String NOTIFICACION_LUEGO_LONGITUD = "Ingrese";
    public boolean enProceso;
    private Sms sms;

    public ServicioEnvioSms() {
        enProceso = false;
        ComunicadorPuertoSerial.obtenerInstancia().addObservers(this);
    }

    public void enviarMensaje(Sms sms, ComunicadorPuertoSerial comunicadorPuertoSerial) {
        if (enProceso) {
            throw new RuntimeException("En proceso...");
        } else {
            this.sms = sms;
            comunicadorPuertoSerial.enviarCadenaDeTexto(sms.comandoArduino());
            enProceso = true;
        }
    }

    @Override
    public void update(Observable o, Object text) {
        System.out.println(text);
        if (text == null) {
            return;
        }
        if (text.toString().contains(NOTIFICACION_LUEGO_COMANDO_ARDUINO)) {
            System.out.println("Recibido notificacion llego comando");
            for (int i = 0; i < sms.getDestinatario().length(); i++) {
                ComunicadorPuertoSerial.obtenerInstancia().enviarCadenaDeTexto(sms.getDestinatario().substring(i, i + 1));
            }
        } else if (text.toString().contains(NOTIFICACION_LUEGO_DESTINATARIO)) {
            System.out.println("Recibido notificacion llego destinatario");
            ComunicadorPuertoSerial.obtenerInstancia().enviarCadenaDeTexto(String.valueOf(sms.getContenido().length()));
        } else if (text.toString().contains(NOTIFICACION_LUEGO_LONGITUD)) {
            System.out.println("Recibido notificacion llego LONGITUD");
            for (int i = 0; i < sms.getContenido().length(); i++) {
                ComunicadorPuertoSerial.obtenerInstancia().enviarCadenaDeTexto(sms.getContenido().substring(i, i + 1));
            }
            System.out.println("ENVIO REALIZADO");
            enProceso = false;
        }
    }
}
