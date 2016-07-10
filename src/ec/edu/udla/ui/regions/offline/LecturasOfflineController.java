package ec.edu.udla.ui.regions.offline;

import ec.edu.udla.arduino.listener.ModoOnlineListener;
import ec.edu.udla.domain.LecturaGlucometro;
import ec.edu.udla.domain.LecturaOffLine;
import ec.edu.udla.domain.Paciente;
import ec.edu.udla.domain.dao.LecturaOffLineDao;
import ec.edu.udla.domain.dao.PacienteDao;
import ec.edu.udla.domain.util.Context;
import ec.edu.udla.reportes.ReporteLecturaGlucometro;
import ec.edu.udla.ui.regions.AbstractController;
import ec.edu.udla.ui.regions.custom.DateAxis;
import javafx.application.HostServices;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

import java.io.File;
import java.net.URL;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class LecturasOfflineController extends AbstractController implements Initializable {

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
        ContextMenu contextMenu = new ContextMenu();
        MenuItem mark = new MenuItem("Marcar como leido");
        mark.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                LecturaOffLine lecturaOffLine = lecturas.getSelectionModel().getSelectedItem();
                lecturaOffLineDao.marcarComoLeido(lecturaOffLine);
                List<LecturaOffLine> lecturasGlucometro = lecturaOffLineDao.findAll();
                lecturas.setItems(FXCollections.observableArrayList(lecturasGlucometro));
            }
        });
        contextMenu.getItems().add(mark);
        lecturas.setContextMenu(contextMenu);

        lecturas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                LecturaOffLine lecturaOffLine = lecturas.getSelectionModel().getSelectedItem();
                lecturaOffLineDao.marcarComoLeido(lecturaOffLine);
                List<LecturaOffLine> lecturasGlucometro = lecturaOffLineDao.findAll();
                lecturas.setItems(FXCollections.observableArrayList(lecturasGlucometro));
            }
        });

        ModoOnlineListener.getInstance().setLecturaOffLine(lecturas);

    }

    private TableView<LecturaOffLine> createTable() {

        List<LecturaOffLine> lecturasGlucometro = lecturaOffLineDao.findAll();
        this.lecturas.setItems(FXCollections.observableArrayList(lecturasGlucometro));
        lecturas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        addColumnToTable(createColumn("Valor (mg/dl)", "valor", 20), lecturas);
        addColumnToTable(createColumn("Fecha de la medicion", "fechaFormateada", 30), lecturas);
        addColumnToTable(createColumn("Ayunas/Con comida", "estado", 20), lecturas);
        addColumnToTable(createColumn("Paciente", "nombrePaciente", 30), lecturas);
        lecturas.setRowFactory(new Callback<TableView<LecturaOffLine>, TableRow<LecturaOffLine>>() {
            @Override
            public TableRow<LecturaOffLine> call(TableView<LecturaOffLine> tableView) {
                final TableRow<LecturaOffLine> row = new TableRow<LecturaOffLine>() {
                    @Override
                    protected void updateItem(LecturaOffLine row, boolean empty) {
                        super.updateItem(row, empty);
                        if (!empty) {
                            styleProperty().bind(Bindings.when(new SimpleBooleanProperty(row.isLeido()))
                                    .then("")
                                    .otherwise("-fx-font-weight: bold;"));
                        }
                    }
                };
                return row;
            }
        });
        return lecturas;
    }

}
