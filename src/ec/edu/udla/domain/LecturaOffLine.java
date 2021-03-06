package ec.edu.udla.domain;

import ec.edu.udla.domain.dao.PacienteDao;

import java.text.SimpleDateFormat;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class LecturaOffLine extends PojoBase {

    public static final String PATTERN = "dd-MM-yyyy hh:mm:ss a";
    private String valor;
    private Date insercion;
    private String fechaInsercionFormateada;
    private Date fecha;
    private EstadoPacienteEnLaLectura estado;
    private int idPaciente;
    private String cedulaPaciente;
    private String fechaFormateada;
    private String nombrePaciente;

    public boolean isLeido() {
        return leido;
    }

    public void setLeido(boolean leido) {
        this.leido = leido;
    }

    private boolean leido;

    public String getNombrePaciente() {
        if(idPaciente!=0){
            Paciente paciente = new PacienteDao().buscarPacientePorId(idPaciente);
            nombrePaciente = paciente.getFullName();
        }
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public static class Buider {
        private LecturaOffLine lecturaOffLine;

        public Buider(){
            lecturaOffLine =  new LecturaOffLine();
        }

        public LecturaOffLine fromLecturaGlucosa(LecturaGlucometro lecturaGlucometro){
            lecturaOffLine.setLeido(false);
            lecturaOffLine.setValor(lecturaGlucometro.getValor());
            lecturaOffLine.setFecha(lecturaGlucometro.getFecha());
            lecturaOffLine.setEstado(lecturaGlucometro.getEstado());
            lecturaOffLine.setIdPaciente (lecturaGlucometro.getIdPaciente());
            lecturaOffLine.setCedulaPaciente(lecturaGlucometro.getCedulaPaciente());
            return lecturaOffLine;
        }
    }

    public String getFechaInsercionFormateada() {
    	if (insercion != null) {

    		ZonedDateTime fechaZone = ZonedDateTime.ofInstant(insercion.toInstant(), ZoneOffset.UTC);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a");
            return fechaZone.format(formatter);
        }
        return fechaInsercionFormateada;
    }

    public void setFechaInsercionFormateada(String fechaInsercionFormateada) {
        this.fechaInsercionFormateada = fechaInsercionFormateada;
    }

    public String getFechaFormateada() {
        if (fecha != null) {
        	Calendar calendar = Calendar.getInstance();
        	calendar.setTime(fecha);
        	calendar.add(Calendar.HOUR, 5);
            ZonedDateTime fechaZone = ZonedDateTime.ofInstant(calendar.getTime().toInstant(), ZoneOffset.UTC);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN);
            return fechaZone.format(formatter);
        }
        return fechaFormateada;
    }

    public void setFechaFormateada(String fechaFormateada) {
        this.fechaFormateada = fechaFormateada;
    }

    public LecturaOffLine() {
        super();
        this.insercion =  new Date();
        setFecha(new Date());
    }

    public Date getInsercion() {
        return insercion;
    }

    public void setInsercion(Date insercion) {
        this.insercion = insercion;
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
