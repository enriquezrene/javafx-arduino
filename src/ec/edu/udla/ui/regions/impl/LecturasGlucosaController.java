package ec.edu.udla.ui.regions.impl;

import ec.edu.udla.domain.LecturaGlucometro;
import ec.edu.udla.domain.Paciente;
import ec.edu.udla.domain.dao.PacienteDao;
import ec.edu.udla.domain.util.Context;
import ec.edu.udla.ui.regions.DrawableRegion;
import ec.edu.udla.ui.regions.RegionsContainer;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class LecturasGlucosaController implements Initializable, DrawableRegion {

    protected RegionsContainer container;
    private PacienteDao pacienteDao;
    private Paciente paciente;

    @FXML
    private ImageView imageView;

    @FXML
    private TableView<LecturaGlucometro> lecturas;


    public LecturasGlucosaController() {
        this.pacienteDao = new PacienteDao();
        lecturas = new TableView<>();
        this.paciente = (Paciente) Context.getInstance().get(Context.PACIENTE_EN_MEMORIA);
    }


    @Override
    public void setContainer(RegionsContainer container) {
        this.container = container;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createTable();
    }

    private TableView<LecturaGlucometro> createTable() {
        this.lecturas.setItems(FXCollections.observableArrayList(pacienteDao.buscarLecturasGlucosa(paciente.getId())));
        lecturas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        addColumnToTable(createColumn("Valor", "valor", 50), lecturas);
        addColumnToTable(createColumn("Fecha de la medicion", "fecha", 50), lecturas);
        return lecturas;
    }

    private void addColumnToTable(TableColumn column, TableView table) {
        table.getColumns().add(column);
    }

    private TableColumn createColumn(String columnHeader, String fieldName, int size) {
        TableColumn column = new TableColumn<>(columnHeader);
        column.setCellValueFactory(new PropertyValueFactory<>(fieldName));
        column.setMaxWidth(1f * Integer.MAX_VALUE * size);
        return column;
    }

}
