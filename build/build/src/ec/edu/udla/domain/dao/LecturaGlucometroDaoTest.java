package ec.edu.udla.domain.dao;

import ec.edu.udla.domain.LecturaGlucometro;
import ec.edu.udla.domain.PojoBase;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 
 */
public class LecturaGlucometroDaoTest {


    @Test
    public void laConsultaDeLecturasDebeRetornarUnaListaNoNula() {
        assertNotNull(new LecturaGlucometroDao().findAll());
    }

    @Test
    public void alGuardarUnNuevoRegistroSeDebeAumentarLosRegistrosEnLaBase() {

        LecturaGlucometroDao lecturaDao = new LecturaGlucometroDao();
        int cantidadActual = lecturaDao.findAll().size();

        lecturaDao.save(new LecturaGlucometro());

        int cantidadLuegoInsert = lecturaDao.findAll().size();
        assertEquals(cantidadActual + 1, cantidadLuegoInsert);
    }
}
