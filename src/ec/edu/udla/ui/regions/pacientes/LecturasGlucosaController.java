package ec.edu.udla.ui.regions.pacientes;

import ec.edu.udla.domain.LecturaGlucometro;
import ec.edu.udla.domain.Paciente;
import ec.edu.udla.domain.dao.PacienteDao;
import ec.edu.udla.domain.util.Context;
import ec.edu.udla.reportes.ReporteLecturaGlucometro;
import ec.edu.udla.ui.regions.AbstractController;
import ec.edu.udla.ui.regions.custom.DateAxis;
import javafx.application.HostServices;
import javafx.application.Platform;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.File;
import java.net.URL;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class LecturasGlucosaController extends AbstractController implements Initializable {

    private PacienteDao pacienteDao;
    private Paciente paciente;

    @FXML
    private TableView<LecturaGlucometro> lecturas;

    @FXML
    private Label infoPaciente;

    @FXML
    private StackPane chartPane;

    @FXML
    private DatePicker fechaInicio, fechaFin;

    @FXML
    private ImageView loading;


    public LecturasGlucosaController() {
        this.pacienteDao = new PacienteDao();
        lecturas = new TableView<>();
        this.paciente = (Paciente) Context.getInstance().get(Context.PACIENTE_EN_MEMORIA);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createTable(pacienteDao.buscarLecturasGlucosa(paciente.getId()));
        populateChart(pacienteDao.buscarLecturasGlucosa(paciente.getId()));
        infoPaciente.setText("Paciente: " + paciente.getApellido() + " " + paciente.getNombre());
        loading.setVisible(false);
    }

    private void populateChart(List<LecturaGlucometro> lecturas) {
        ObservableList<XYChart.Series<Date, Number>> series = FXCollections.observableArrayList();

        ObservableList<XYChart.Data<Date, Number>> medidasGlucosa = FXCollections.observableArrayList();

        for (LecturaGlucometro lectura : lecturas) {
            medidasGlucosa.add(new XYChart.Data<Date, Number>(lectura.getFecha(), Double.valueOf(lectura.getValor())));
        }

        series.add(new XYChart.Series<>("Mediciones realizadas con el glucometro", medidasGlucosa));


        NumberAxis numberAxis = new NumberAxis();
        numberAxis.setLabel("Valor de glucosa");
        DateAxis dateAxis = new DateAxis();
        LineChart<Date, Number> lineChart = new LineChart<>(dateAxis, numberAxis, series);
        chartPane.getChildren().clear();
        chartPane.getChildren().addAll(lineChart);
    }

    private TableView<LecturaGlucometro> createTable(List<LecturaGlucometro> lecturasGlucometro) {
        this.lecturas.setItems(FXCollections.observableArrayList(lecturasGlucometro));
        lecturas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        addColumnToTable(createColumn("Valor", "valor", 50), lecturas);
        addColumnToTable(createColumn("Fecha de la medicion", "fecha", 50), lecturas);
        return lecturas;
    }


    public void filtrar(ActionEvent event) {
        List<LecturaGlucometro> lecturaGlucometros = pacienteDao.buscarLecturasGlucosaEntreFechas(paciente.getId(),
                Date.from(fechaInicio.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                Date.from(fechaFin.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        populateChart(lecturaGlucometros);
        this.lecturas.setItems(FXCollections.observableArrayList(lecturaGlucometros));
        lecturas.refresh();

    }

    public void exportar(ActionEvent actionEvent) {
        HostServices hostServices = this.container.getHostServices();
        Task<File> task = new Task<File>() {
            @Override public File call() {
                try {
                    File file = new ReporteLecturaGlucometro(lecturas.getItems(), paciente.getFullName()).getReporteAsPDF();
                    hostServices.showDocument(file.toURI().toURL().toExternalForm());
                    return file;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

        task.setOnRunning((e) -> loading.setVisible(true));
        task.setOnSucceeded((e) -> {loading.setVisible(false);});
        task.setOnFailed((e) -> {loading.setVisible(false);});
        new Thread(task).start();
    }
}
