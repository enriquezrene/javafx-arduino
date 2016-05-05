package ec.edu.udla.arduino.sms.service;

import ec.edu.udla.arduino.comunicacion.ComunicadorPuertoSerial;
import ec.edu.udla.arduino.sms.domain.Sms;

public class ServicioEnvioSms {

	public static final String NOTIFICACION_LUEGO_COMANDO_ARDUINO = "Ingrese telefono celular";

	public void enviarMensaje(Sms sms, ComunicadorPuertoSerial comunicadorPuertoSerial) {
		System.out.println("enviando sms: "+sms);
//		comunicadorPuertoSerial.enviarCadenaDeTexto(sms.comandoArduino());
//
//		for (int i = 0; i < sms.getDestinatario().length(); i++) {
//			comunicadorPuertoSerial.enviarCadenaDeTexto(sms.getDestinatario().substring(i, i + 1));
//		}
//		comunicadorPuertoSerial.enviarCadenaDeTexto(String.valueOf(sms.getContenido().length()));
//		for (int i = 0; i < sms.getContenido().length(); i++) {
//			comunicadorPuertoSerial.enviarCadenaDeTexto(sms.getContenido().substring(i, i + 1));
//		}
	}

}
