package ec.edu.udla.reportes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Created by moe on 5/9/16.
 */
public class ArchivoHtml {

    private String direccionDelArchivo;

    public ArchivoHtml(String direccionDelArchivo) {
        this.direccionDelArchivo = direccionDelArchivo;
    }

    public String asString() {
        File file = new File(direccionDelArchivo);
        byte[] bytes = new byte[0];
        try {
            bytes = Files.readAllBytes(file.toPath());
            return new String(bytes, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
