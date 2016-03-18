package ec.edu.udla.tesis.arduino.lector.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import ec.edu.udla.tesis.arduino.lector.LectorInformacion;
import ec.edu.udla.tesis.domain.EstadoPacienteEnLaLectura;
import ec.edu.udla.tesis.domain.LecturaGlucometro;

public class LectorInformacionEnMemoria implements LectorInformacion {

	@Override
	public List<LecturaGlucometro> extraerDatos() {

		List<LecturaGlucometro> lecturas = crearLecturas(30);
		return lecturas;
	}

	private List<LecturaGlucometro> crearLecturas(int cantidad) {
		String[] nombres = { "Juan", "Mathy", "Rene", "Mayra", "Johana" };
		String[] apellidos = { "Guaman", "Enriquez", "Lopez", "Olmedo", "Veintimilla" };
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());

		EstadoPacienteEnLaLectura[] estados = EstadoPacienteEnLaLectura.values();

		List<LecturaGlucometro> lecturas = new ArrayList<>();
		for (int i = 0; i < cantidad; i++) {
			LecturaGlucometro lecturaGlucometro = new LecturaGlucometro();
			lecturaGlucometro.setEstado(estados[i % 2]);
			lecturaGlucometro.setNombrePaciente(nombres[i % 5] + " " + apellidos[4 - (i % 5)]);
			calendar.add(Calendar.MONTH, new Random().nextInt());
			lecturaGlucometro.setFechaHora(calendar.getTime());
			lecturaGlucometro.setLecturaGlucosa(new Random().nextInt(1200) + "");

			lecturas.add(lecturaGlucometro);
		}
		return lecturas;
	}

}
