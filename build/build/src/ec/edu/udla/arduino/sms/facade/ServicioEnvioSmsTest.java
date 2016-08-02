package ec.edu.udla.arduino.sms.facade;

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import ec.edu.udla.arduino.comunicacion.ComunicadorPuertoSerial;
import ec.edu.udla.arduino.sms.domain.Sms;
import ec.edu.udla.arduino.sms.service.ServicioEnvioSms;

public class ServicioEnvioSmsTest {

	private static final String DESTINATARIO = "1234567890";

	@Test
	public void alEnviarUnSmsSeEnviar1ElComando2ElDestinatario3LongitudMensaje4ContenidoenviarNumeroDeTelefonoDeCaracterEnCaracter()
			throws Exception {

		ComunicadorPuertoSerial comunicadorPuertoSerial = mock(ComunicadorPuertoSerial.class);
		Sms sms = new Sms.Builder().conDestinatario(DESTINATARIO).build();
		ServicioEnvioSms servicioSms = new ServicioEnvioSms();

		servicioSms.enviarMensaje(sms, comunicadorPuertoSerial);

		validarEnvioComando(comunicadorPuertoSerial, sms);
		validarEnvioDelDestinatarioCaracterPorCaracter(comunicadorPuertoSerial, sms);
		validarEnvioLongitudMensaje(comunicadorPuertoSerial, sms);
		validarEnvioDelContenidoCaracterPorCaracter(comunicadorPuertoSerial, sms);
	}

	private void validarEnvioDelContenidoCaracterPorCaracter(ComunicadorPuertoSerial comunicadorPuertoSerial, Sms sms) {
		String contenido = sms.getContenido();
		for (int i = 0; i < contenido.length(); i++) {
			verify(comunicadorPuertoSerial, atLeast(1)).enviarCadenaDeTexto(contenido.substring(i, i + 1));
		}

	}

	private void validarEnvioLongitudMensaje(ComunicadorPuertoSerial comunicadorPuertoSerial, Sms sms) {
		verify(comunicadorPuertoSerial, times(1)).enviarCadenaDeTexto(String.valueOf(sms.getContenido().length()));

	}

	private void validarEnvioComando(ComunicadorPuertoSerial comunicadorPuertoSerial, Sms sms) {
		verify(comunicadorPuertoSerial, times(1)).enviarCadenaDeTexto(sms.comandoArduino());
	}

	private void validarEnvioDelDestinatarioCaracterPorCaracter(ComunicadorPuertoSerial comunicadorPuertoSerial,
			Sms sms) {
		String destinatario = sms.getDestinatario();
		for (int i = 0; i < destinatario.length(); i++) {
			verify(comunicadorPuertoSerial, times(1)).enviarCadenaDeTexto(destinatario.substring(i, i + 1));
		}

	}

}
