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
import javafx.scene.Node;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class IngresarPacienteController implements Initializable, DrawableRegion {

	private static final int REGISTROS_POR_PAGINA = 15;
	protected RegionsContainer container;

	@FXML
	private TextField nombre, apellido, cedula, telefono, email, direccion, peso, estatura;

	@FXML
	private GridPane gridPane;

	@FXML
	private ImageView imageView;

	private TableView<Paciente> pacientes;
	private List<Paciente> pacientesEncontrados;

	public IngresarPacienteController() {
		pacientes = createTable();
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

		Pagination pagination = new Pagination((pacientesEncontrados.size() / REGISTROS_POR_PAGINA + 1), 0);
		pagination.setPageFactory(this::createPage);
		gridPane.add(new StackPane(pagination), 0, 11, 5, 1);
	}

	private TableView<Paciente> createTable() {

		pacientes = new TableView<Paciente>();

		TableColumn<Paciente, String> columnaNombrePaciente = createColumn("Nombre", "nombre");
		TableColumn<Paciente, String> columnaApellidoPaciente = createColumn("Apellido", "apellido");
		TableColumn<Paciente, String> columnaCedulaPaciente = createColumn("Cedula", "cedula");
		TableColumn<Paciente, String> columnaEmailPaciente = createColumn("Email", "email");

		columnaNombrePaciente.setMinWidth(500);
		columnaApellidoPaciente.setMinWidth(500);
		columnaCedulaPaciente.setMinWidth(300);
		columnaEmailPaciente.setMinWidth(300);// 1f * Integer.MAX_VALUE * 25);

		pacientes.getColumns().add(columnaApellidoPaciente);
		pacientes.getColumns().add(columnaNombrePaciente);
		pacientes.getColumns().add(columnaCedulaPaciente);
		pacientes.getColumns().add(columnaEmailPaciente);

		return pacientes;
	}

	private TableColumn<Paciente, String> createColumn(String columnHeader, String fieldName) {
		TableColumn<Paciente, String> column = new TableColumn<>(columnHeader);
		column.setCellValueFactory(new PropertyValueFactory<>(fieldName));
		return column;
	}

	private Node createPage(int pageIndex) {
		int fromIndex = pageIndex * REGISTROS_POR_PAGINA;
		int toIndex = Math.min(fromIndex + REGISTROS_POR_PAGINA, pacientesEncontrados.size());
		pacientes.setItems(FXCollections.observableArrayList(pacientesEncontrados.subList(fromIndex, toIndex)));
		return new StackPane(pacientes);
	}

}
