package ec.edu.udla.reportes;

import com.itextpdf.text.DocumentException;
import ec.edu.udla.domain.LecturaGlucometro;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 
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

    public File getFilasAsExcelFile() throws IOException {
        File file = new File("docs/" + new Date().getTime() + ".xls");
        List<HSSFRow> filasExcel = new ArrayList<>();
        HSSFWorkbook libroExcel = new HSSFWorkbook();
        HSSFSheet hojaExcel = libroExcel.createSheet("reporte");

        HSSFRow row = hojaExcel.createRow(0);


        HSSFCellStyle style = libroExcel.createCellStyle();
        HSSFFont font = libroExcel.createFont();
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setFontHeightInPoints((short)12);
        font.setBold(true);
        style.setFont(font);
        row.createCell(0).setCellValue("VALOR (mg/dl)");
        row.createCell(1).setCellValue("FECHA DE LA MEDICION");

        row.getCell(0).setCellStyle(style);
        row.getCell(1).setCellStyle(style);

        filasExcel.add(row);

        for (int i = 1; i <= lecturas.size(); i++) {

            row = hojaExcel.createRow(i);

            Cell cellValor = row.createCell(0);
            cellValor.setCellValue(lecturas.get(i - 1).getValor());

            Cell cellFecha = row.createCell(1);
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            cellFecha.setCellValue(format.format(lecturas.get(i - 1).getFecha()));

            filasExcel.add(row);
        }
        hojaExcel.autoSizeColumn(0);
        hojaExcel.autoSizeColumn(1);

        FileOutputStream out = new FileOutputStream(file);
        libroExcel.write(out);
        return file;
    }
}
