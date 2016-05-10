package ec.edu.udla.reportes;

import com.itextpdf.text.DocumentException;
import ec.edu.udla.domain.LecturaGlucometro;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * Created by rene on 10/05/16.
 */
public class ReporteLecturaGlucometro {

    private List<LecturaGlucometro> lecturas;
    private TextoComoFilaHtml textoComoFilaHtml;
    private String nombrePaciente;

    public ReporteLecturaGlucometro(List<LecturaGlucometro> lecturas) {
        this.lecturas = lecturas;
        this.nombrePaciente = "Nombre no especificado";
    }

    public ReporteLecturaGlucometro(List<LecturaGlucometro> lecturas, String nombrePaciente) {
        this.lecturas = lecturas;
        this.nombrePaciente = nombrePaciente;
    }


    public File getReporteAsPDF() throws IOException, DocumentException {
        String filas = getLecturasAsHtmlString();
        String tabla = new TextoComoFilaHtml("docs/html_header_table.html", filas).leer();
        TextoComoFilaHtml textoComoFilaHtml = new TextoComoFilaHtml("docs/html_header_file.html", nombrePaciente, tabla);
        return textoComoFilaHtml.asPdfFile();
    }

    public List<String> getLecturasComoHtml() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        List<String> lecturasComoHtml = new ArrayList<>();
        for (LecturaGlucometro lecturaGlucometro : lecturas) {
            lecturasComoHtml.add(new TextoComoFilaHtml("docs/html_row_table.html", lecturaGlucometro.getValor(), formatter.format(lecturaGlucometro.getFecha())).leer());
        }
        return lecturasComoHtml;
    }

    public String getLecturasAsHtmlString() {
        StringBuilder cadenaHtml = new StringBuilder();
        List<String> lecturasAsHtml = getLecturasComoHtml();
        for (String lectura : lecturasAsHtml) {
            cadenaHtml.append(lectura);
        }
        return cadenaHtml.toString();
    }
}
