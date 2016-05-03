package ec.edu.udla.arduino.sms.domain;

/**
 * Created by moe on 5/3/16.
 */
public class Sms {

	private String destinatario;
	public String contenido;

	public static class Builder {

		private Sms sms;

		public Builder() {
			this.sms = new Sms();
		}

		public Sms build() {
			if (sms.destinatario == null) {
				throw new IllegalStateException("No se ha especificado un destinatario");
			}
			if (sms.contenido == null) {
				conContenido(null);
			}
			return sms;
		}

		public Builder conDestinatario(String destinatario) {
			if (destinatario == null || destinatario.length() != 10) {
				throw new IllegalArgumentException("El valor no es valido:" + destinatario);
			}
			sms.destinatario = destinatario;
			return this;
		}

		public Builder conContenido(String contenido) {
			if (contenido == null) {
				contenido = "";
			}

			int longitudActual = contenido.length();
			StringBuilder contenidoConTamanioDeseado = new StringBuilder(contenido);

			if (longitudActual < 100) {
				int longitudRestantePara100 = 100 - longitudActual;
				StringBuilder cadenaVaciaNecesariaParaCompletar = new StringBuilder();
				while (cadenaVaciaNecesariaParaCompletar.length() < longitudRestantePara100) {
					cadenaVaciaNecesariaParaCompletar.append(" ");
				}
				contenidoConTamanioDeseado.insert(longitudActual, cadenaVaciaNecesariaParaCompletar);

			} else if (longitudActual > 100) {
				contenidoConTamanioDeseado.setLength(100);
			}
			sms.contenido = contenidoConTamanioDeseado.toString();
			return this;
		}

	}

	public String comandoArduino() {
		return "M";
	}

	public String getContenido() {
		return contenido;
	}

	public String getDestinatario() {
		return this.destinatario;
	}

}
