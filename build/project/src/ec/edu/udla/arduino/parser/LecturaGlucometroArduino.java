package ec.edu.udla.arduino.parser;

import ec.edu.udla.domain.EstadoPacienteEnLaLectura;
import ec.edu.udla.domain.LecturaGlucometro;

/**
 
 */
public class LecturaGlucometroArduino {


    private String cadenaArduino;
    private String[] arregloCadenas;

    public LecturaGlucometroArduino(String cadenaArduino) {
        this.cadenaArduino = cadenaArduino;
        parsearCadenaComoArreglo(cadenaArduino);
    }

    private void parsearCadenaComoArreglo(String cadenaArduino) {
        this.arregloCadenas = cadenaArduino.split(" ");
        if (arregloCadenas.length < 4) {
            throw new IllegalArgumentException("Cadena invalida: " + cadenaArduino);
        }
        if (getEstado() == null) {
            throw new IllegalArgumentException("Cadena invalida: " + cadenaArduino);
        }
    }

    private EstadoPacienteEnLaLectura getEstado() {
        EstadoPacienteEnLaLectura estado = null;
        if (arregloCadenas[3].equals("A")) {
            estado = EstadoPacienteEnLaLectura.AYUNAS;
        } else if (arregloCadenas[3].equals("C")) {
            estado = EstadoPacienteEnLaLectura.CON_COMIDA;
        }
        return estado;
    }

    public String[] obtenerArregloDeCadenas() {

        return arregloCadenas;
    }

    public LecturaGlucometro obtenerObjeto() {
        LecturaGlucometro lecturaGlucometro = new LecturaGlucometro();
        lecturaGlucometro.setCedulaPaciente(arregloCadenas[0]);
        lecturaGlucometro.setValor(arregloCadenas[1]);
        lecturaGlucometro.setFecha(new FechaHexadecimal(arregloCadenas[2]).asDate());
        lecturaGlucometro.setEstado(getEstado());
        return lecturaGlucometro;
    }


}
