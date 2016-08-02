package ec.edu.udla.domain.dao;

import ec.edu.udla.domain.LecturaGlucometro;
import ec.edu.udla.domain.Paciente;
import ec.edu.udla.domain.PojoBase;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 
 */
public class PacienteDaoTest {

    @Test
    public void siElPacienteEsHalladoSeDebeRetornarElId() {
        String cedula = "1714545669";
        PacienteDao pacienteDao = new PacienteDao();
        int idObtenido = pacienteDao.buscarPacientePorCedula(cedula).getId();
        int idDelPaciente = 6;
        assertEquals(idDelPaciente, idObtenido);
    }

    @Test
    public void siElPacienteNoEsHalladoDebeRetornalUnPacienteNull() {
        String cedula = "1714545669000";
        PacienteDao pacienteDao = new PacienteDao();
        Paciente pacienteHallado = pacienteDao.buscarPacientePorCedula(cedula);
        assertEquals(Paciente.NEW, pacienteHallado);
    }

    @Test
    public void alRegistrarUnaLecturaDelArduinoSiElPacienteNoEsHalladoSeDebeCrearUnoNuevoConLaCedulaDeLaLectura() {
        PacienteDao pacienteDao = mock(PacienteDao.class);


        LecturaGlucometro lectura = new LecturaGlucometro();
        lectura.setCedulaPaciente("XYZ");

        doCallRealMethod().when(pacienteDao).registrarLectura(lectura);
        when(pacienteDao.buscarPacientePorCedula("XYZ")).thenReturn(Paciente.NEW);

        pacienteDao.registrarLectura(lectura);

        ArgumentCaptor argument = ArgumentCaptor.forClass(Paciente.class);
        verify(pacienteDao, times(1)).saveAndGetId((PojoBase) argument.capture());

        assertEquals("XYZ", ((Paciente) argument.getValue()).getCedula());
    }

    @Test
    public void siElPacienteEsHalladoNoSeDebeGuardarUnoNuevo() {
        PacienteDao pacienteDao = mock(PacienteDao.class);
        LecturaGlucometro lectura = new LecturaGlucometro();
        lectura.setCedulaPaciente("XYZ");

        Paciente paciente = new Paciente();
        paciente.setId(99);
        paciente.setCedula("XYZ");

        doCallRealMethod().when(pacienteDao).registrarLectura(lectura);
        when(pacienteDao.buscarPacientePorCedula("XYZ")).thenReturn(paciente);

        pacienteDao.registrarLectura(lectura);

        verify(pacienteDao, times(0)).saveAndGetId(any(PojoBase.class));
    }

    @Test
    public void paraRegistrarUnaLecturaSeDebeUsarElIdDelPacienteRecienRegistrado() {
        int idDelPacienteRegistrado = 99;
        LecturaGlucometro lectura= new LecturaGlucometro();
        lectura.setCedulaPaciente("1");

        ArgumentCaptor argument = ArgumentCaptor.forClass(LecturaGlucometro.class);
        LecturaGlucometroDao lecturaDao = mock(LecturaGlucometroDao.class);

        PacienteDao pacienteDao = mock(PacienteDao.class);

        doCallRealMethod().when(pacienteDao).setLecturaGlucometroDao(lecturaDao);
        pacienteDao.setLecturaGlucometroDao(lecturaDao);

        Paciente p = new Paciente();
        p.setCedula("1");
        p.setId(idDelPacienteRegistrado);
        when(pacienteDao.buscarPacientePorCedula("1")).thenReturn(p);

        doCallRealMethod().when(pacienteDao).registrarLectura(lectura);

        pacienteDao.registrarLectura(lectura);


        verify(lecturaDao, times(1)).save((PojoBase) argument.capture());
        assertEquals(idDelPacienteRegistrado, ((LecturaGlucometro) argument.getValue()).getIdPaciente());
    }


    @Test
    public void seDebeRetornarUnIdAlRegistrarUnNuevoPaciente() {
        PacienteDao pacienteDao = new PacienteDao();
        int idPacienteInsertado = pacienteDao.saveAndGetId(new Paciente());
        assertTrue(0 != idPacienteInsertado);
        pacienteDao.delete(idPacienteInsertado);
    }
}
