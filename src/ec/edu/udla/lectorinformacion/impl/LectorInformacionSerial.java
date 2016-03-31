package ec.edu.udla.lectorinformacion.impl;

import java.util.ArrayList;
import java.util.List;

import ec.edu.udla.domain.LecturaGlucometro;
import ec.edu.udla.lectorinformacion.LectorInformacion;

public class LectorInformacionSerial implements LectorInformacion {

	public List<LecturaGlucometro> extraerDatos() {
		return new ArrayList<>();
	}

}
