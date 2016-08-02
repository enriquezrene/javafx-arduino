package ec.edu.udla.arduino.parser;

import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;

/**
 * Created by rene on 09/05/16.
 */
public class FechaHexadecimalTest {

    @Test
    public void laFechaEsperadaEs_11_02_2016(){

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new FechaHexadecimal("56BCB920").asDate());
        assertEquals(11, calendar.get(Calendar.DAY_OF_MONTH));
        assertEquals(Calendar.FEBRUARY, calendar.get(Calendar.MONTH));
        assertEquals(2016, calendar.get(Calendar.YEAR));
    }
}
