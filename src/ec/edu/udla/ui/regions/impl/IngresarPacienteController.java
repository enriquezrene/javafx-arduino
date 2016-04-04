package ec.edu.udla.ui.regions.impl;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import ec.edu.udla.domain.Paciente;
import ec.edu.udla.facade.PacientesFacade;
import ec.edu.udla.ui.regions.DrawableRegion;
import ec.edu.udla.ui.regions.RegionsContainer;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class IngresarPacienteController implements Initializable, DrawableRegion {

	protected RegionsContainer container;

	@FXML
	private TextField nombre, apellido, cedula, telefono, email, direccion, peso, estatura;

	@FXML
	private GridPane gridPane;

	@FXML
	private ImageView imageView;

	@FXML
	private TableView<Paciente> pacientes;

	private List<Paciente> pacientesEncontrados;

	public IngresarPacienteController() {
		// pacientes = createTable();
		pacientesEncontrados = new PacientesFacade().crearListaPacientes(77);
	}

	@FXML
	private void guardarDatosPaciente() {
		System.out.println("Ingresando: " + nombre.getText() + apellido.getText() + cedula.getText());
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

		pacientes.setItems(FXCollections.observableArrayList(pacientesEncontrados));
		pacientes.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		addColumnToTable(createColumn("Nombre", "nombre"), pacientes);
		addColumnToTable(createColumn("Apellido", "apellido"), pacientes);
		addColumnToTable(createColumn("Cedula", "cedula"), pacientes);
		addColumnToTable(createColumn("Email", "email"), pacientes);

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
