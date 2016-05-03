package ec.edu.udla.facade;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import ec.edu.udla.domain.LecturaGlucometro;
import ec.edu.udla.domain.Paciente;
import ec.edu.udla.domain.dao.PacienteDao;
import ec.edu.udla.facade.PacientesFacade;

public class PacientesFacadeTest {

	@Test
	public void obtenerLecturasAPartirDelIdPaciente() {
		PacienteDao pacienteDao = new PacienteDao();
		int idPaciente = 3;
		List<LecturaGlucometro> lecturas = pacienteDao.buscarLecturasGlucosa(idPaciente);
		Assert.assertNotNull(lecturas);
	}

	@Test
	public void debeRetornarTantosPacientesComoSeSolicite() throws Exception {
		PacientesFacade pacientesFacade = new PacientesFacade();
		int pacientesSolicitados = 7;
		List<Paciente> pacientes = pacientesFacade.crearListaPacientes(pacientesSolicitados);
		Assert.assertEquals(pacientesSolicitados, pacientes.size());
	}

}
