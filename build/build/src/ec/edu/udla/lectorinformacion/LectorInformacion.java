package ec.edu.udla.lectorinformacion;

import java.util.List;

import ec.edu.udla.domain.LecturaGlucometro;

public interface LectorInformacion {

	List<LecturaGlucometro> extraerDatos();

}