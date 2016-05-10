package ec.edu.udla.arduino.parser;

/**
 * Created by rene on 09/05/16.
 */
public class NumeroHexadecimal {

    private String representacionHexadecimal;

    public NumeroHexadecimal(String valor){
        this.representacionHexadecimal = valor;
    }

    public int obtenerRepresentacionDecimal(){
        return Integer.parseInt(representacionHexadecimal, 16);
    }
}
