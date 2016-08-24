package ec.edu.udla.ui.regions.pacientes;

import java.io.File;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TimeZone;

import ec.edu.udla.domain.LecturaGlucometro;
import ec.edu.udla.domain.Paciente;
import ec.edu.udla.domain.dao.PacienteDao;
import ec.edu.udla.domain.util.Context;
import ec.edu.udla.reportes.ReporteLecturaGlucometro;
import ec.edu.udla.ui.regions.AbstractController;
import ec.edu.udla.ui.regions.custom.DateAxis;
import javafx.application.HostServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class LecturasGlucosaController extends AbstractController implements Initializable {

    private PacienteDao pacienteDao;
    private Paciente paciente;

    @FXML
    private TableView<LecturaGlucometro> lecturas;

    @FXML
    private Label infoPaciente, telefono;

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
        telefono.setText("Celular: "+paciente.getTelefono());
    }

    private void populateChart(List<LecturaGlucometro> lecturas) {
        ObservableList<XYChart.Series<Date, Number>> series = FXCollections.observableArrayList();

        ObservableList<XYChart.Data<Date, Number>> medidasGlucosa = FXCollections.observableArrayList();

        for (LecturaGlucometro lectura : lecturas) {

//        	SimpleDateFormat isoFormat = new SimpleDateFormat("yyy-MM-dd'T'HH:mm:ss");
//            String fecha = isoFormat.format(lectura.getFecha());
//            isoFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
//            try {
//                lectura.setFecha(isoFormat.parse(fecha));
//                XYChart.Data<Date, Number> data = new XYChart.Data<Date, Number>(isoFormat.parse(fecha), Double.valueOf(lectura.getValor()));
        	Calendar c = Calendar.getInstance();
        	c.setTime(lectura.getFecha());
        	c.add(Calendar.HOUR, 5);
//        	 XYChart.Data<Date, Number> data = new XYChart.Data<Date, Number>(lectura.getFecha(), Double.valueOf(lectura.getValor()));
        	 XYChart.Data<Date, Number> data = new XYChart.Data<Date, Number>(c.getTime(), Double.valueOf(lectura.getValor()));
                data.setNode(new HoveredThresholdNode(0, lectura.getFechaFormateada()));
                medidasGlucosa.add(data);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }


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
        addColumnToTable(createColumn("Valor (mg/dl)", "valor", 30), lecturas);
        addColumnToTable(createColumn("Fecha de la medicion", "fechaFormateada", 40), lecturas);
        addColumnToTable(createColumn("Ayunas/Con comida", "estado", 40), lecturas);
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

        task.setOnRunning((e) -> {loading.setVisible(true); loading.setManaged(true);});
        task.setOnSucceeded((e) -> {loading.setVisible(false); loading.setManaged(false);});
        task.setOnFailed((e) -> {loading.setVisible(false); loading.setManaged(false);});
        new Thread(task).start();
    }

    public void exportarXls(ActionEvent actionEvent) {
        HostServices hostServices = this.container.getHostServices();
        Task<File> task = new Task<File>() {
            @Override public File call() {
                try {
                    File file = new ReporteLecturaGlucometro(lecturas.getItems(), paciente.getFullName()).getFilasAsExcelFile();
                    hostServices.showDocument(file.toURI().toURL().toExternalForm());
                    return file;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

        task.setOnRunning((e) -> {loading.setVisible(true); loading.setManaged(true);});
        task.setOnSucceeded((e) -> {loading.setVisible(false); loading.setManaged(false);});
        task.setOnFailed((e) -> {loading.setVisible(false); loading.setManaged(false);});
        new Thread(task).start();
    }
}

class HoveredThresholdNode extends StackPane {
    HoveredThresholdNode(int priorValue, String value) {
      setPrefSize(15, 15);

      final Label label = createDataThresholdLabel(priorValue, value);

      setOnMouseEntered(new EventHandler<MouseEvent>() {
        @Override public void handle(MouseEvent mouseEvent) {
          getChildren().setAll(label);
          setCursor(Cursor.NONE);
          toFront();
        }
      });
      setOnMouseExited(new EventHandler<MouseEvent>() {
        @Override public void handle(MouseEvent mouseEvent) {
          getChildren().clear();
          setCursor(Cursor.CROSSHAIR);
        }
      });
    }

    private Label createDataThresholdLabel(int priorValue, String value) {
      final Label label = new Label(value);
      label.getStyleClass().addAll("default-color0", "chart-line-symbol", "chart-series-line");
      label.setStyle("-fx-font-size: 12; -fx-font-weight: bold;");

//      if (priorValue == 0) {
//        label.setTextFill(Color.DARKGRAY);
//      } else if (value > priorValue) {
//        label.setTextFill(Color.FORESTGREEN);
//      } else {
        label.setTextFill(Color.FIREBRICK);
//      }

      label.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
      return label;
    }
  }

