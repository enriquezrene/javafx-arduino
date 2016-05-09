package ec.edu.udla.domain;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by rene on 09/05/16.
 */
public class PacienteTest {

    @Test
    public void testDosPacienteSonIgualesSiSusIdsYCedulasLoSon() {
        int id = 9;
        String cedula = "123";

        Paciente pacienteUno = new Paciente();
        pacienteUno.setId(id);

        pacienteUno.setCedula(cedula);

        Paciente pacienteDos = new Paciente();
        pacienteDos.setId(id);
        pacienteDos.setCedula(cedula);

        assertTrue(pacienteUno.equals(pacienteDos));
    }
}
