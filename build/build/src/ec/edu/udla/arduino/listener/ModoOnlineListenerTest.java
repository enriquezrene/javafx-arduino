package ec.edu.udla.arduino.listener;

import ec.edu.udla.arduino.parser.LecturaGlucometroArduino;
import ec.edu.udla.domain.LecturaGlucometro;
import ec.edu.udla.domain.dao.PacienteDao;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

/**
 
 */
public class ModoOnlineListenerTest {

    @Test
    public void testCuandoLlegueUnaCadenaDebeObtenerseUnaLecturaSiLaCadenaEsCorrecta() {

        String cadenaArduino = "254 93 56BCB920 C";
        ModoOnlineListener modoOnlineListener = mock(ModoOnlineListener.class);
        doCallRealMethod().when(modoOnlineListener).update(null, cadenaArduino);


        modoOnlineListener.update(null, cadenaArduino);

        verify(modoOnlineListener, times(1)).procesarLectura(cadenaArduino);
    }

    @Test
    public void alProcesarLecturaSeDebeGuardarUsandoElDao() {

        String cadenaArduino = "254 93 56BCB920 C";
        ModoOnlineListener modoOnlineListener = mock(ModoOnlineListener.class);
        modoOnlineListener.pacienteDao = mock(PacienteDao.class);
        doCallRealMethod().when(modoOnlineListener).procesarLectura(cadenaArduino);

        modoOnlineListener.procesarLectura(cadenaArduino);

        ArgumentCaptor<LecturaGlucometro> argumentCaptor = ArgumentCaptor.forClass(LecturaGlucometro.class);
        verify(modoOnlineListener.pacienteDao, times(1)).registrarLectura(argumentCaptor.capture());
        assertEquals("254", argumentCaptor.getValue().getCedulaPaciente());
    }
}
