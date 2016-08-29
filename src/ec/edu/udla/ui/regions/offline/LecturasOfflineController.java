package ec.edu.udla.ui.regions.offline;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import ec.edu.udla.arduino.listener.ModoOnlineListener;
import ec.edu.udla.domain.LecturaOffLine;
import ec.edu.udla.domain.dao.LecturaOffLineDao;
import ec.edu.udla.ui.regions.AbstractController;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

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
                for (LecturaOffLine lecturaOffLine2 : lecturasGlucometro) {

				}
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
