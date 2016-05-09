package ec.edu.udla.arduino.parser;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by rene on 09/05/16.
 */
public class NumeroHexadecimalTest {

    @Test
    public void testConversionHexadecimalADecimal() {
            assertEquals(1455208736, new NumeroHexadecimal("56BCB920").obtenerRepresentacionDecimal());
    }
}
