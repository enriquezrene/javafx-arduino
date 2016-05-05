package ec.edu.udla.arduino.sms.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Created by moe on 5/3/16.
 */
public class SmsBuilder {

	private static final String DESTINATARIO = "1234567890";

	@Test
	public void siNoSeEspecificaUnContenidoSeDebeLlenarConUnaCadenaDeEspaciosVaciosDeTamanio100() {
		Sms sms = new Sms.Builder().conDestinatario(DESTINATARIO).build();
		assertEquals(100, sms.getContenido().length());
	}

	@Test(expected = IllegalStateException.class)
	public void unMensajeSiempreDebeTenerDestinatarioCasoContrarioSeArrojaUnError() {
		new Sms.Builder().build();
	}

	@Test
	public void testElComandoParaEnvioDeSmsEs_M() {
		Sms sms = new Sms.Builder().conDestinatario(DESTINATARIO).build();
		assertTrue("M".equals(sms.comandoArduino()));
	}

	@Test
	public void siElNumeroDeCelularTiene10DigitosEsValido() {
		Sms sms = new Sms.Builder().conDestinatario(DESTINATARIO).build();
		assertEquals(10, sms.getDestinatario().length());
	}

	@Test(expected = IllegalArgumentException.class)
	public void siElNumeroDeCelularNoTiene10DigitosEsUnNumeroInvalido() {
		new Sms.Builder().conDestinatario("1").build();
	}

	@Test
	public void siElContenidoEsNuloSeCreaUnaCadenaDeEspaciosVacios() {
		Sms sms = new Sms.Builder().conContenido(null).conDestinatario(DESTINATARIO).build();
		assertEquals(100, sms.getContenido().length());
	}

	@Test
	public void losMensajesConMenosDe100CaracteresSeRellenanDeEspaciosHasta100() {
		Sms sms = new Sms.Builder().conContenido("Hola").conDestinatario(DESTINATARIO).build();
		assertEquals(100, sms.getContenido().length());
	}

	@Test
	public void siElContenidoProvistoEsMayorA100ElMensajeSeTruncaA100Caracteres() {
		StringBuilder contenidoExcedeTamanio = new StringBuilder();

		while (contenidoExcedeTamanio.length() < 120) {
			contenidoExcedeTamanio.append("a");
		}
		Sms sms = new Sms.Builder().conDestinatario(DESTINATARIO).conContenido(contenidoExcedeTamanio.toString())
				.build();
		assertEquals(100, sms.getContenido().length());
	}
}
