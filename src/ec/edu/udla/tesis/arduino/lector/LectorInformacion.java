package ec.edu.udla.tesis.arduino.lector;

import java.util.List;

import ec.edu.udla.tesis.domain.LecturaGlucometro;

public interface LectorInformacion {

	List<LecturaGlucometro> extraerDatos();

}