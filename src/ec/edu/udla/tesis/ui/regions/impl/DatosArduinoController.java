// MyPasswordFieldController.java - Controller class for MyPasswordField.fxml
package ec.edu.udla.tesis.ui.regions.impl;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import ec.edu.udla.tesis.arduino.lector.LectorInformacion;
import ec.edu.udla.tesis.arduino.lector.impl.LectorInformacionEnMemoria;
import ec.edu.udla.tesis.domain.EstadoPacienteEnLaLectura;
import ec.edu.udla.tesis.domain.LecturaGlucometro;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class DatosArduinoController implements Initializable {

	@FXML
	private TableView<LecturaGlucometro> lecturasRealizadas;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		ObservableList<LecturaGlucometro> datosLeidos = FXCollections.observableArrayList();
		LectorInformacion lectorInformacion;

		lectorInformacion = new LectorInformacionEnMemoria();
		datosLeidos.addAll(lectorInformacion.extraerDatos());

		lecturasRealizadas.setItems(datosLeidos);

		lecturasRealizadas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		TableColumn<LecturaGlucometro, String> columnaNombrePaciente = new TableColumn<>("Nombre");
		columnaNombrePaciente.setCellValueFactory(new PropertyValueFactory<>("nombrePaciente"));

		TableColumn<LecturaGlucometro, String> columnaValorGlucosa = new TableColumn<>("Glucosa");
		columnaValorGlucosa.setCellValueFactory(new PropertyValueFactory<>("lecturaGlucosa"));

		TableColumn<LecturaGlucometro, Date> columnaFechaMedicion = new TableColumn<>("Fecha");
		columnaFechaMedicion.setCellValueFactory(new PropertyValueFactory<>("fechaHora"));

		TableColumn<LecturaGlucometro, EstadoPacienteEnLaLectura> columnaEstado = new TableColumn<>("Estado");
		columnaEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

		columnaNombrePaciente.setMaxWidth(1f * Integer.MAX_VALUE * 40); // 50%
																		// width
		columnaValorGlucosa.setMaxWidth(1f * Integer.MAX_VALUE * 10); // 30%
																		// width
		columnaFechaMedicion.setMaxWidth(1f * Integer.MAX_VALUE * 30); // 20%
																		// width
		columnaEstado.setMaxWidth(1f * Integer.MAX_VALUE * 20); // 20% width

		lecturasRealizadas.getColumns().add(columnaNombrePaciente);
		lecturasRealizadas.getColumns().add(columnaValorGlucosa);
		lecturasRealizadas.getColumns().add(columnaFechaMedicion);
		lecturasRealizadas.getColumns().add(columnaEstado);
	}
}
