package ec.edu.udla.tesis.arduino.lector.impl;

import java.util.ArrayList;
import java.util.List;

import ec.edu.udla.tesis.arduino.lector.LectorInformacion;
import ec.edu.udla.tesis.domain.LecturaGlucometro;

public class LectorInformacionSerial implements LectorInformacion {

	public List<LecturaGlucometro> extraerDatos() {
		return new ArrayList<>();
	}

}
