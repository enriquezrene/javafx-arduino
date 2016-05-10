package ec.edu.udla.arduino.parser;

import ec.edu.udla.domain.EstadoPacienteEnLaLectura;
import ec.edu.udla.domain.LecturaGlucometro;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by rene on 09/05/16.
 */
public class LectorGlucometroDesdeArduinoTest {

    @Test
    public void unaCadenaValidaDebeTener4CadenasSeparadasPorUnEspacio() {
        LecturaGlucometroArduino lectorGlucometroDesdeArduino = new LecturaGlucometroArduino("254 93 56BCB920 C");
        String cadenas[] = lectorGlucometroDesdeArduino.obtenerArregloDeCadenas();
        assertEquals(4, cadenas.length);
    }


    @Test
    public void laSegundaCadenaRepresentaElValorDeLaGlucosa() {
        LecturaGlucometroArduino lector = new LecturaGlucometroArduino("254 93 56BCB920 C");
        LecturaGlucometro lecturaGlucometro = lector.obtenerObjeto();
        String[] arregloCadenas = lector.obtenerArregloDeCadenas();
        int posicionDos = 1;
        assertEquals(lecturaGlucometro.getValor(), arregloCadenas[posicionDos]);
    }

    @Test
    public void laPrimeraCadenaRepresentaLaCedulaDelPaciente() {
        LecturaGlucometroArduino lector = new LecturaGlucometroArduino("254 93 56BCB920 C");
        LecturaGlucometro lecturaGlucometro = lector.obtenerObjeto();
        assertEquals(lecturaGlucometro.getCedulaPaciente(), lector.obtenerArregloDeCadenas()[0]);
    }

    @Test
    public void seDebeObtenerUnObjetoLecturaGlucometro() {
        LecturaGlucometroArduino lector = new LecturaGlucometroArduino("254 93 56BCB920 C");
        assertTrue(lector.obtenerObjeto() instanceof LecturaGlucometro);
    }

    @Test(expected = IllegalArgumentException.class)
    public void siNoSeProveenTresCadenasSeparadasPorUnEspacioArrojaUnError() {
        new LecturaGlucometroArduino("254 93 C");
    }

    @Test
    public void valoresValidosParaLaUltimaCadenaSonA_o_C() {
        LecturaGlucometroArduino lectorConA = new LecturaGlucometroArduino("254 93 56BCB920 A");
        String cadenasConA[] = lectorConA.obtenerArregloDeCadenas();
        assertEquals("A", cadenasConA[3]);

        LecturaGlucometroArduino lectorConC = new LecturaGlucometroArduino("254 93 56BCB920 C");
        String cadenasConC[] = lectorConC.obtenerArregloDeCadenas();
        assertEquals("C", cadenasConC[3]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void siLaUltimaCadenaNoEsA_o_C_DebeArrojarUnError() {
        LecturaGlucometroArduino lectorCadenas = new LecturaGlucometroArduino("254 93 56BCB920 X");
        lectorCadenas.obtenerArregloDeCadenas();
    }

    @Test
    public void laCadena3EsUnaFechaEnFormatoUnix() {
        LecturaGlucometroArduino lector = new LecturaGlucometroArduino("254 93 56BCB920 C");
        LecturaGlucometro lectura = lector.obtenerObjeto();
        assertTrue(lectura.getFecha() instanceof Date);
    }

    @Test
    public void siLaCuartaCadenaEsA_Lectura_Ayunas(){
        LecturaGlucometroArduino lector = new LecturaGlucometroArduino("254 93 56BCB920 A");
        LecturaGlucometro lectura = lector.obtenerObjeto();
        assertEquals(EstadoPacienteEnLaLectura.AYUNAS, lectura.getEstado());
    }

    @Test
    public void siLaCuartaCadenaEsC_ConComida(){
        LecturaGlucometroArduino lector = new LecturaGlucometroArduino("254 93 56BCB920 C");
        LecturaGlucometro lectura = lector.obtenerObjeto();
        assertEquals(EstadoPacienteEnLaLectura.CON_COMIDA, lectura.getEstado());
    }

}
