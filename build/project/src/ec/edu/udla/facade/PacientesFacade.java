package ec.edu.udla.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ec.edu.udla.domain.Paciente;

public class PacientesFacade {

	public List<Paciente> crearListaPacientes(int cantidadRequerida) {
		List<Paciente> pacientes = new ArrayList<Paciente>();
		String[] nombres = { "Juan", "Mathy", "Rene", "Mayra", "Johana" };
		String[] apellidos = { "Guaman", "Enriquez", "Lopez", "Olmedo", "Veintimilla" };
		String[] cedulas = { "1234567890", "7894560012", "7894521100", "0014578966", "7784558971" };
		String[] telefonos = { "1234567890", "7894560012", "7894521100", "0014578966", "7784558971" };
		String[] direcciones = { "San Juan", "La Merced", "Nayon", "La Ecuatoriana", "San Carlos" };

		for (int i = 0; i < cantidadRequerida; i++) {
			Paciente paciente = new Paciente();
			paciente.setApellido(apellidos[i % 5]);
			paciente.setNombre(nombres[4 - i % 5]);
			paciente.setCedula(cedulas[i % 5]);
			paciente.setDireccion(direcciones[4 - i % 5]);
			paciente.setEmail(paciente.getNombre().toLowerCase() + "@" + paciente.getApellido().toLowerCase() + ".com");
			paciente.setEstatura(new Random().nextInt(17) + "");
			paciente.setPeso(new Random().nextInt(220) + "");
			paciente.setTelefono(telefonos[4 - i % 5]);
			pacientes.add(paciente);
		}
		return pacientes;
	}
}
