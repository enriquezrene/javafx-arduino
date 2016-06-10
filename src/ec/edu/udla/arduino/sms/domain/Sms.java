package ec.edu.udla.arduino.sms.domain;

/**
 * Created by moe on 5/3/16.
 */
public class Sms {

	private String destinatario;
	private String contenido;

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

			int MAX_LONGITUD = 71;
			if (longitudActual < MAX_LONGITUD) {
				int longitudRestantePara100 = MAX_LONGITUD - longitudActual;
				StringBuilder cadenaVaciaNecesariaParaCompletar = new StringBuilder();
				while (cadenaVaciaNecesariaParaCompletar.length() < longitudRestantePara100) {
					cadenaVaciaNecesariaParaCompletar.append(" ");
				}
				contenidoConTamanioDeseado.insert(longitudActual, cadenaVaciaNecesariaParaCompletar);

			} else if (longitudActual > MAX_LONGITUD) {
				contenidoConTamanioDeseado.setLength(MAX_LONGITUD);
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

	@Override
	public String toString() {
		return "[destinatario:"+destinatario+" contenido:"+contenido+"]";
	}
}
