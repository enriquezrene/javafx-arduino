package ec.edu.udla.arduino.sms.service;

import ec.edu.udla.arduino.comunicacion.ComunicadorPuertoSerial;
import ec.edu.udla.arduino.sms.domain.Sms;

import java.util.Observable;
import java.util.Observer;

public class ServicioEnvioSms implements Observer {

    public static final String NOTIFICACION_LUEGO_COMANDO_ARDUINO = "celular";
    public static final String NOTIFICACION_LUEGO_DESTINATARIO = "caracteres";
    public static final String NOTIFICACION_LUEGO_LONGITUD = "Ingrese";
    private Sms sms;

    public ServicioEnvioSms() {
        ComunicadorPuertoSerial.obtenerInstancia().addObservers(this);
    }

    public void enviarMensaje(Sms sms, ComunicadorPuertoSerial comunicadorPuertoSerial) {

        this.sms = sms;
        comunicadorPuertoSerial.enviarCadenaDeTexto(sms.comandoArduino());

    }

    @Override
    public void update(Observable o, Object text) {
        System.out.println(text);
        if (text == null) {
            return;
        }
        if (text.toString().contains(NOTIFICACION_LUEGO_COMANDO_ARDUINO)) {
            System.out.println("Recibido notificacion llego comando");
            ComunicadorPuertoSerial.obtenerInstancia().enviarCadenaDeTexto(sms.getDestinatario());
        } else if (text.toString().contains(NOTIFICACION_LUEGO_DESTINATARIO)) {
            System.out.println("Recibido notificacion llego destinatario");
            ComunicadorPuertoSerial.obtenerInstancia().enviarCadenaDeTexto("0");
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ComunicadorPuertoSerial.obtenerInstancia().enviarCadenaDeTexto("9");
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ComunicadorPuertoSerial.obtenerInstancia().enviarCadenaDeTexto("9");
        } else if (text.toString().contains(NOTIFICACION_LUEGO_LONGITUD)) {
            System.out.println("Recibido notificacion llego LONGITUD");
            ComunicadorPuertoSerial.obtenerInstancia().enviarCadenaDeTexto(sms.getContenido());
            System.out.println("ENVIO REALIZADO");
            ComunicadorPuertoSerial.obtenerInstancia().removeObservers(this);
        }
    }
}
