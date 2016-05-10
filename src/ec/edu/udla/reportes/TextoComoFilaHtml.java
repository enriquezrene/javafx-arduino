package ec.edu.udla.reportes;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by moe on 5/9/16.
 */
public class TextoComoFilaHtml {

    private String plantillaHtml;
    private String[] parametros;
    private String cadenaCompleta;

    public TextoComoFilaHtml(String plantillaHtml, String... parametros) {
        this.plantillaHtml = plantillaHtml;
        this.parametros = parametros;

    }

    public String leerPlantilla() {
        return new ArchivoHtml(plantillaHtml).asString();
    }


    public String leer() {
        cadenaCompleta = String.format(leerPlantilla(), parametros);
        return cadenaCompleta;
    }

    public File asHtmlFile() throws IOException {
        leer();
        File file = new File("docs/report.html");
        if (file.exists()) {
            file.delete();
        }
        Files.write(Paths.get("docs/report.html"), cadenaCompleta.getBytes());
        return file;
    }

    public File asPdfFile() throws IOException, DocumentException {
        File file = new File("docs/report.pdf");
        if (file.exists()) {
            file.delete();
        }
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
        writer.setInitialLeading(12);
        document.open();
        XMLWorkerHelper.getInstance().parseXHtml(writer, document,
                new FileInputStream(asHtmlFile()));
        document.close();
        return file;
    }
}
