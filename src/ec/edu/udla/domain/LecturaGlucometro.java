package ec.edu.udla.domain;

import java.util.Date;

public class LecturaGlucometro {

	private int id;
	private String valor;
	private Date fecha;
	private EstadoPacienteEnLaLectura estado;
	private int idPaciente;
	private String cedulaPaciente;

	public void printInfo(){
		System.out.println(cedulaPaciente);
		System.out.println(estado);
		System.out.println(fecha);
		System.out.println(valor);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public EstadoPacienteEnLaLectura getEstado() {
		return estado;
	}

	public void setEstado(EstadoPacienteEnLaLectura estado) {
		this.estado = estado;
	}

	public int getIdPaciente() {
		return idPaciente;
	}

	public void setIdPaciente(int idPaciente) {
		this.idPaciente = idPaciente;
	}

	public String getCedulaPaciente() {
		return cedulaPaciente;
	}

	public void setCedulaPaciente(String cedulaPaciente) {
		this.cedulaPaciente = cedulaPaciente;
	}
}
