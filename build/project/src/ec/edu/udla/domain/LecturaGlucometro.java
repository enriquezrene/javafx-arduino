package ec.edu.udla.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class LecturaGlucometro extends PojoBase {

    private String valor;
    private Date fecha;
    private EstadoPacienteEnLaLectura estado;
    private int idPaciente;
    private String cedulaPaciente;
    private String fechaFormateada;

    public String getFechaFormateada() {
        if (fecha != null) {
            ZonedDateTime fechaZone = ZonedDateTime.ofInstant(fecha.toInstant(), ZoneOffset.UTC);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a");
            return fechaZone.format(formatter);
        }
        return fechaFormateada;
    }

    public void setFechaFormateada(String fechaFormateada) {
        this.fechaFormateada = fechaFormateada;
    }

    public LecturaGlucometro() {
        setFecha(new Date());
    }

    public void printInfo() {
        System.out.println(cedulaPaciente);
        System.out.println(estado);
        System.out.println(fecha);
        System.out.println(valor);
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
