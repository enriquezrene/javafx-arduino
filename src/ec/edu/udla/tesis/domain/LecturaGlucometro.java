package ec.edu.udla.tesis.domain;

import java.util.Date;

public class LecturaGlucometro {

	private String nombrePaciente;
	private String lecturaGlucosa;
	private Date fechaHora;
	private EstadoPacienteEnLaLectura estado;

	public String getNombrePaciente() {
		return nombrePaciente;
	}

	public void setNombrePaciente(String nombrePaciente) {
		this.nombrePaciente = nombrePaciente;
	}

	public String getLecturaGlucosa() {
		return lecturaGlucosa;
	}

	public void setLecturaGlucosa(String lecturaGlucosa) {
		this.lecturaGlucosa = lecturaGlucosa;
	}

	public Date getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}

	public EstadoPacienteEnLaLectura getEstado() {
		return estado;
	}

	public void setEstado(EstadoPacienteEnLaLectura estado) {
		this.estado = estado;
	}

}
