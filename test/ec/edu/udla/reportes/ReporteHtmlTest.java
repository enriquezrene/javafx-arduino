package ec.edu.udla.reportes;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import ec.edu.udla.domain.LecturaGlucometro;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by moe on 5/9/16.
 */
public class ReporteHtmlTest {


    @Test
    public void seDebeGenerarUnArchivoPdfAlEjecutarElReporte() throws IOException, DocumentException {
        List<LecturaGlucometro> lecturas = new ArrayList<>();
        LecturaGlucometro l1 = new LecturaGlucometro();
        l1.setValor("100");
        l1.setFecha(new Date());
        LecturaGlucometro l2 = new LecturaGlucometro();
        l2.setValor("200");
        l2.setFecha(new Date());
        lecturas.add(l1);
        lecturas.add(l2);

        String nombrePaciente = "Rene Enriquez";
        ReporteLecturaGlucometro reporte = new ReporteLecturaGlucometro(lecturas, nombrePaciente);
        File file = reporte.getReporteAsPDF();
        assertTrue(file.exists());
    }

    @Test
    public void laCadenaAUsarParaElReporteEsFormadaAPartirDeLaSumaDeLasFilasComoHtml() {
        List<LecturaGlucometro> lecturas = new ArrayList<>();
        LecturaGlucometro l1 = new LecturaGlucometro();
        l1.setValor("100");
        l1.setFecha(new Date());
        LecturaGlucometro l2 = new LecturaGlucometro();
        l2.setValor("200");
        l2.setFecha(new Date());
        lecturas.add(l1);
        lecturas.add(l2);

        ReporteLecturaGlucometro reporte = new ReporteLecturaGlucometro(lecturas);
        assertNotNull(reporte.getLecturasAsHtmlString());
    }

    @Test
    public void seDebenCrearTantasFilasComoRegistrosHaya() {

        List<LecturaGlucometro> lecturas = new ArrayList<>();
        LecturaGlucometro l1 = new LecturaGlucometro();
        l1.setValor("100");
        l1.setFecha(new Date());
        LecturaGlucometro l2 = new LecturaGlucometro();
        l2.setValor("200");
        l2.setFecha(new Date());
        lecturas.add(l1);
        lecturas.add(l2);

        ReporteLecturaGlucometro reporte = new ReporteLecturaGlucometro(lecturas);
        assertEquals(2, reporte.getLecturasComoHtml().size());
    }

    @Test
    public void seDebeCrearUnArchivoPdfAPartirDelHtml() throws Exception {
        String filaUno = new TextoComoFilaHtml("docs/html_row_table.html", new String[]{"100", "Rene"}).leer();
        String fileDos = new TextoComoFilaHtml("docs/html_row_table.html", new String[]{"200", "Enriquez"}).leer();
        String tabla = new TextoComoFilaHtml("docs/html_header_table.html", new String[]{filaUno + fileDos}).leer();
        TextoComoFilaHtml textoComoFilaHtml = new TextoComoFilaHtml("docs/html_header_file.html", "Rene Enriquez", tabla);
        File documentoPdf = textoComoFilaHtml.asPdfFile();
        assertTrue(documentoPdf.exists());
    }


    @Test
    public void seDebeCrearUnArchivoHtmlConElTextoFormado() throws Exception {
        String filaUno = new TextoComoFilaHtml("docs/html_row_table.html", new String[]{"100", "Rene"}).leer();
        String fileDos = new TextoComoFilaHtml("docs/html_row_table.html", new String[]{"200", "Enriquez"}).leer();
        String tabla = new TextoComoFilaHtml("docs/html_header_table.html", new String[]{filaUno + fileDos}).leer();
        TextoComoFilaHtml documentoHtml = new TextoComoFilaHtml("docs/html_header_file.html", "Rene Enriquez", tabla);
        File archivoHtml = documentoHtml.asHtmlFile();
        assertTrue(archivoHtml.exists());
    }


    @Test
    public void validarCadenasConcatenadas() {
        String filaUno = new TextoComoFilaHtml("docs/html_row_table.html", new String[]{"100", "Rene"}).leer();
        String fileDos = new TextoComoFilaHtml("docs/html_row_table.html", new String[]{"200", "Enriquez"}).leer();
        String tabla = new TextoComoFilaHtml("docs/html_header_table.html", new String[]{filaUno + fileDos}).leer();
        String documentoHtml = new TextoComoFilaHtml("docs/html_header_file.html", "Rene Enriquez", tabla).leer();
        documentoHtml = removeSpacesAndBreakLines(documentoHtml);

        String contenidoCompleto = new ArchivoHtml("docs/table.html").asString();
        contenidoCompleto = removeSpacesAndBreakLines(contenidoCompleto);

        assertEquals(contenidoCompleto, documentoHtml);
    }


    @Test
    public void reemplazarCadenaConComillasDobles() {
        System.out.println(String.format("a \"a\" %s", "rene"));
    }

    @Test
    public void validarCadenasReemplazadasEnPlantilla() {
        String archivoConPlantila = "docs/html_row_table.html";
        TextoComoFilaHtml textoComoHtml = new TextoComoFilaHtml(archivoConPlantila, new String[]{"rene", "enriquez"});
        assertEquals("<tr><td>rene</td><td>enriquez</td></tr>", removeSpacesAndBreakLines(textoComoHtml.leer()));
    }

    private String removeSpacesAndBreakLines(String texto) {
        return texto.replace(" ", "").replace("\n", "");
    }

    @Test
    public void validarValorDeLaPlantillaObtenido() {
        String archivoConPlantila = "docs/html_row_table.html";
        TextoComoFilaHtml textoComoHtml = new TextoComoFilaHtml(archivoConPlantila, null);
        assertEquals("<tr><td>%s</td><td>%s</td></tr>", textoComoHtml.leerPlantilla().replace(" ", "").replace("\n", ""));
    }

    public static final String DEST = "docs/table.pdf";
    public static final String DOCUMENTO_HTML = "docs/table.html";

    public static void main(String[] args) throws IOException, DocumentException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new ReporteHtmlTest().createPdf(DEST);
    }

    /**
     * Creates a PDF with the words "Hello World"
     *
     * @param file
     * @throws IOException
     * @throws DocumentException
     */
    public void createPdf(String file) throws IOException, DocumentException {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
        writer.setInitialLeading(12);
        document.open();
        XMLWorkerHelper.getInstance().parseXHtml(writer, document,
                new FileInputStream(DOCUMENTO_HTML));
        document.close();
    }
}
