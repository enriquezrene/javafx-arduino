package ec.edu.udla.domain;

public class Paciente extends PojoBase {

<<<<<<< HEAD
    public static final Paciente NEW = new Paciente();

=======
>>>>>>> 732f4fa03ac4549a73b960479d6c7001a025a3c7
    private String nombre, apellido, cedula, telefono, email, direccion, peso, estatura;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getEstatura() {
        return estatura;
    }

    public void setEstatura(String estatura) {
        this.estatura = estatura;
    }

<<<<<<< HEAD
    @Override
    public boolean equals(Object obj) {
        Paciente paciente = (Paciente) obj;
        boolean idsIguales = this.id == paciente.getId();
        boolean cedulasIguales = false;
        if (this.cedula == null && paciente.getCedula() == null) {
            cedulasIguales = true;
        } else if (this.cedula != null) {
            cedulasIguales = this.cedula.equals(paciente.getCedula());
        }
        return cedulasIguales && idsIguales;
=======
    public String getFullName() {
        return this.nombre + " " + this.apellido;
>>>>>>> 732f4fa03ac4549a73b960479d6c7001a025a3c7
    }
}
