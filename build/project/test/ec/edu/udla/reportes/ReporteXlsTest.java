package ec.edu.udla.reportes;

import ec.edu.udla.domain.LecturaGlucometro;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by moe on 5/9/16.
 */
public class ReporteXlsTest {


    @Test
    public void seDebenGenerarTantasFilasComoLecturasHayan() throws Exception {


        List<LecturaGlucometro> lecturas = new ArrayList<>();
        lecturas.add(new LecturaGlucometro());
        lecturas.add(new LecturaGlucometro());

        ReporteLecturaGlucometro reporte = new ReporteLecturaGlucometro(lecturas);

        File file = reporte.getFilasAsExcelFile();
        FileInputStream fileIS = new FileInputStream(file);

        HSSFWorkbook workbook = new HSSFWorkbook(fileIS);
        List<HSSFRow> filasExcel = new ArrayList<>();
        filasExcel.add(workbook.getSheet("reporte").getRow(0));
        filasExcel.add(workbook.getSheet("reporte").getRow(1));


        assertEquals(lecturas.size(), filasExcel.size());
    }


    @Test
    public void cadaFilaDebeContenerDosCeldasValorYFechaMedicion() throws Exception {

        List<LecturaGlucometro> lecturas = new ArrayList<>();

        LecturaGlucometro l1 = new LecturaGlucometro();
        l1.setValor("125.22");
        l1.setFecha(new Date());

        LecturaGlucometro l2 = new LecturaGlucometro();
        l2.setValor("555.10");
        l2.setFecha(new Date());

        lecturas.add(l1);
        lecturas.add(l2);


        ReporteLecturaGlucometro reporte = new ReporteLecturaGlucometro(lecturas);
        File file = reporte.getFilasAsExcelFile();
        FileInputStream fileIS = new FileInputStream(file);

        HSSFWorkbook workbook = new HSSFWorkbook(fileIS);
        List<HSSFRow> filasExcel = new ArrayList<>();
        filasExcel.add(workbook.getSheet("reporte").getRow(1));
        filasExcel.add(workbook.getSheet("reporte").getRow(2));

        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        HSSFRow r1 = filasExcel.get(0);
        assertEquals(r1.getCell(0).getStringCellValue(), l1.getValor());
        assertEquals(r1.getCell(1).getStringCellValue(), formatter.format(l1.getFecha()));

        HSSFRow r2 = filasExcel.get(1);
        assertEquals(r2.getCell(0).getStringCellValue(), l2.getValor());
        assertEquals(r2.getCell(1).getStringCellValue(), formatter.format(l2.getFecha()));


    }

}
