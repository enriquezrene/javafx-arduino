package ec.edu.udla.arduino.parser;


public class NumeroHexadecimal {

    private String representacionHexadecimal;

    public NumeroHexadecimal(String valor){
        this.representacionHexadecimal = valor;
    }

    public int obtenerRepresentacionDecimal(){
        return Integer.parseInt(representacionHexadecimal, 16);
    }
}
