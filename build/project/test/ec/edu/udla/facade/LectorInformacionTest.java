package ec.edu.udla.facade;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import ec.edu.udla.domain.LecturaGlucometro;
import ec.edu.udla.lectorinformacion.LectorInformacion;
import ec.edu.udla.lectorinformacion.impl.LectorInformacionEnMemoria;
import ec.edu.udla.lectorinformacion.impl.LectorInformacionSerial;

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
