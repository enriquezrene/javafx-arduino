package ec.edu.udla.arduino.parser;

import java.util.Date;

/**
 
 */
public class FechaHexadecimal {


    private String valorHexadecimal;
    private long fechaEnSegundos;

    public FechaHexadecimal(String valorHexadecimal) {
        this.valorHexadecimal = valorHexadecimal;
        this.fechaEnSegundos = new NumeroHexadecimal(valorHexadecimal).obtenerRepresentacionDecimal();
    }

    public Date asDate() {
        long asMilis = fechaEnSegundos * 1000;
        return new Date(asMilis);
    }

}
