package ec.edu.udla.ui.regions.offline;

import ec.edu.udla.domain.LecturaGlucometro;
import ec.edu.udla.domain.LecturaOffLine;
import ec.edu.udla.domain.Paciente;
import ec.edu.udla.domain.dao.LecturaOffLineDao;
import ec.edu.udla.domain.dao.PacienteDao;
import ec.edu.udla.reportes.ReporteLecturaGlucometro;
import ec.edu.udla.ui.regions.AbstractController;
import ec.edu.udla.ui.regions.custom.DateAxis;
import javafx.application.HostServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.File;
import java.net.URL;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class LecturasOfflineController extends AbstractController implements Initializable {

    private LecturaOffLineDao lecturaOnLineDao;
    private LecturaOffLineDao lecturaOffLineDao;

    @FXML
    private TableView<LecturaOffLine> lecturas;

    public LecturasOfflineController() {
        lecturas = new TableView<>();
        this.lecturaOffLineDao = new LecturaOffLineDao();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createTable();
    }

    private TableView<LecturaOffLine> createTable() {
        List<LecturaOffLine> lecturasGlucometro = lecturaOffLineDao.findAll();
        this.lecturas.setItems(FXCollections.observableArrayList(lecturasGlucometro));
        lecturas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        addColumnToTable(createColumn("Valor", "valor", 20), lecturas);
        addColumnToTable(createColumn("Fecha de la medicion", "fechaFormateada", 30), lecturas);
        addColumnToTable(createColumn("Ayunas/Con comida", "estado", 20), lecturas);
        addColumnToTable(createColumn("Paciente", "nombrePaciente", 30), lecturas);
        return lecturas;
    }

}
