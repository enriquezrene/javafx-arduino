package ec.edu.udla.reportes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Created by moe on 5/9/16.
 */
public class TextoComoFilaHtml{

    private String plantillaHtml;
    private String[] parametros;

    public TextoComoFilaHtml(String plantillaHtml, String... parametros) {
        this.plantillaHtml = plantillaHtml;
        this.parametros=parametros;

    }

    public String leerPlantilla(){
        return new ArchivoHtml(plantillaHtml).asString();
    }



    public String leer() {
        return String.format(leerPlantilla(), parametros);
    }
}
