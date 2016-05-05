package ec.edu.udla.ui.regions.pacientes;

import ec.edu.udla.domain.LecturaGlucometro;
import ec.edu.udla.domain.Paciente;
import ec.edu.udla.domain.dao.PacienteDao;
import ec.edu.udla.domain.util.Context;
import ec.edu.udla.ui.regions.AbstractController;
import ec.edu.udla.ui.regions.RegionsContainer;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class LecturasGlucosaController extends AbstractController implements Initializable {

    protected RegionsContainer container;
    private PacienteDao pacienteDao;
    private Paciente paciente;

    @FXML
    private TableView<LecturaGlucometro> lecturas;


    public LecturasGlucosaController() {
        this.pacienteDao = new PacienteDao();
        lecturas = new TableView<>();
        this.paciente = (Paciente) Context.getInstance().get(Context.PACIENTE_EN_MEMORIA);
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


}
