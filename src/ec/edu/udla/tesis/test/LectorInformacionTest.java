package ec.edu.udla.tesis.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import ec.edu.udla.tesis.arduino.lector.LectorInformacion;
import ec.edu.udla.tesis.arduino.lector.impl.LectorInformacionEnMemoria;
import ec.edu.udla.tesis.arduino.lector.impl.LectorInformacionSerial;
import ec.edu.udla.tesis.domain.LecturaGlucometro;

public class LectorInformacionTest {

	@Test
	public void elLectorDeInformacionDebeRetornarUnaListaNoNulaDeLecturas() throws Exception {
		LectorInformacion lectorInformacion = new LectorInformacionSerial();
		List<LecturaGlucometro> lecturasEncontradas = lectorInformacion.extraerDatos();
		Assert.assertNotNull(lecturasEncontradas);
	}

	@Test
	public void elLectorDePruebasRetornaUnaListaCon30Registros() throws Exception {
		LectorInformacion lectorInformacion = new LectorInformacionEnMemoria();
		List<LecturaGlucometro> lecturasDeTest = lectorInformacion.extraerDatos();
		Assert.assertEquals(30, lecturasDeTest.size());
	}

}
