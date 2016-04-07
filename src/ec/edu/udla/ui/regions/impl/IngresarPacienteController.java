package ec.edu.udla.ui.regions.impl;

import java.net.URL;
import java.util.ResourceBundle;

import ec.edu.udla.domain.Paciente;
import ec.edu.udla.domain.dao.PacienteDao;
import ec.edu.udla.ui.regions.DrawableRegion;
import ec.edu.udla.ui.regions.RegionsContainer;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class IngresarPacienteController implements Initializable, DrawableRegion {

	protected RegionsContainer container;
	private PacienteDao pacienteDao;
	private Paciente paciente;

	@FXML
	private TextField nombre, apellido, cedula, telefono, email, direccion, peso, estatura;

	@FXML
	private GridPane gridPane;

	@FXML
	private ImageView imageView;

	@FXML
	private TableView<Paciente> pacientes;

	Integer statusAction;
	public static Integer statusConnection;

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
		limpiarTextField(apellido, cedula, direccion, email, estatura, nombre, peso, telefono);
		paciente = null;
	}

	private void limpiarTextField(TextField... textFields) {
		for (int i = 0; i < textFields.length; i++) {
			textFields[i].setText("");
		}
	}

	@Override
	public void setContainer(RegionsContainer container) {
		this.container = container;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		createTable();
	}

	private TableView<Paciente> createTable() {

		pacientes.setItems(FXCollections.observableArrayList(pacienteDao.findAll()));
		pacientes.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		addColumnToTable(createColumn("Nombre", "nombre"), pacientes);
		addColumnToTable(createColumn("Apellido", "apellido"), pacientes);
		addColumnToTable(createColumn("Cedula", "cedula"), pacientes);
		addColumnToTable(createColumn("Email", "email"), pacientes);

		ContextMenu menu = new ContextMenu();
		MenuItem editarMenuItem = new MenuItem("Editar");
		MenuItem eliminarMenuItem = new MenuItem("Eliminar");
		menu.getItems().addAll(editarMenuItem, eliminarMenuItem);
		pacientes.setContextMenu(menu);

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

	private void addColumnToTable(TableColumn<Paciente, String> column, TableView<Paciente> table) {
		table.getColumns().add(column);
	}

	private TableColumn<Paciente, String> createColumn(String columnHeader, String fieldName) {
		TableColumn<Paciente, String> column = new TableColumn<>(columnHeader);
		column.setCellValueFactory(new PropertyValueFactory<>(fieldName));
		column.setMaxWidth(1f * Integer.MAX_VALUE * 25);
		return column;
	}

}
