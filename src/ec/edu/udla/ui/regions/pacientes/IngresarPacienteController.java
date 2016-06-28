package ec.edu.udla.ui.regions.pacientes;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import ec.edu.udla.arduino.listener.ModoOnlineListener;
import ec.edu.udla.domain.Paciente;
import ec.edu.udla.domain.dao.PacienteDao;
import ec.edu.udla.domain.util.Context;
import ec.edu.udla.ui.MainApp;
import ec.edu.udla.ui.regions.AbstractController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class IngresarPacienteController extends AbstractController implements Initializable {


    private PacienteDao pacienteDao;
    private Paciente paciente;

    @FXML
    private TextField nombre, apellido, cedula, telefono, email, direccion, peso, estatura;


    @FXML
    private TableView<Paciente> pacientes;

    public IngresarPacienteController() {
        pacienteDao = new PacienteDao();
    }

    @FXML
    private void guardarDatosPaciente() {
        if (paciente == null) {
            paciente = new Paciente();
        }
        paciente.setApellido(apellido.getText());
        paciente.setCedula(cedula.getText());
        paciente.setDireccion(direccion.getText());
        paciente.setEmail(email.getText());
        paciente.setEstatura(estatura.getText());
        paciente.setNombre(nombre.getText());
        paciente.setPeso(peso.getText());
        paciente.setTelefono(telefono.getText());
        pacienteDao.saveOrUpdate(paciente);
        pacientes.setItems(FXCollections.observableArrayList(pacienteDao.findAll()));
        limpiarTextInputs(apellido, cedula, direccion, email, estatura, nombre, peso, telefono);
        pacientes.refresh();
        paciente = null;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createTable();
        ModoOnlineListener.getInstance().setTable(pacientes);
    }

    private TableView<Paciente> createTable() {

        pacientes.setItems(FXCollections.observableArrayList(pacienteDao.findAll()));
//        pacientes.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        addColumnToTable(createColumn("Nombre", "nombre", 25), pacientes);
        addColumnToTable(createColumn("Apellido", "apellido", 25), pacientes);
        addColumnToTable(createColumn("Id del paciente", "cedula", 25), pacientes);
        addColumnToTable(createColumn("Email", "email", 25), pacientes);

        ContextMenu menu = new ContextMenu();
        MenuItem editarMenuItem = new MenuItem("Editar");
        MenuItem eliminarMenuItem = new MenuItem("Eliminar");
        MenuItem mostrarValoresGlucosaItem = new MenuItem("Ver medidas de glucosa");
        menu.getItems().addAll(editarMenuItem, eliminarMenuItem, mostrarValoresGlucosaItem);
        pacientes.setContextMenu(menu);

        mostrarValoresGlucosaItem.setOnAction(event -> {
            Context.getInstance().put(Context.PACIENTE_EN_MEMORIA, pacientes.getSelectionModel().getSelectedItem());
            try {
                container.loadScreen(MainApp.PANTALLA_MEDIDAS_GLUCOSA,
                        LecturasGlucosaController.class.getResource(MainApp.PANTALLA_MEDIDAS_GLUCOSA));
            } catch (IOException e) {
                e.printStackTrace();
            }
            container.setCurrentScreen(MainApp.PANTALLA_MEDIDAS_GLUCOSA);
        });

        editarMenuItem.setOnAction(event -> {
            paciente = pacientes.getSelectionModel().getSelectedItem();
            nombre.setText(paciente.getNombre());
            apellido.setText(paciente.getApellido());
            cedula.setText(paciente.getCedula());
            email.setText(paciente.getEmail());
            direccion.setText(paciente.getDireccion());
            peso.setText(paciente.getPeso());
            estatura.setText(paciente.getEstatura());
            telefono.setText(paciente.getTelefono());
        });

        eliminarMenuItem.setOnAction(event -> {
            Paciente paciente = pacientes.getSelectionModel().getSelectedItem();
            pacienteDao.delete(paciente.getId());
            pacientes.setItems(FXCollections.observableArrayList(pacienteDao.findAll()));
        });

        return pacientes;
    }


}
