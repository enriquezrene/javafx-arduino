package ec.edu.udla.ui.regions.login.impl;

import java.net.URL;
import java.util.ResourceBundle;

import ec.edu.udla.facade.AdministracionUsuariosFacade;
import ec.edu.udla.ui.MainApp;
import ec.edu.udla.ui.regions.DrawableRegion;
import ec.edu.udla.ui.regions.RegionsContainer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController implements Initializable, DrawableRegion {

	private RegionsContainer regionsContainer;

	private AdministracionUsuariosFacade facade;

	@FXML
	private TextField usuario;

	@FXML
	private PasswordField password;

	public LoginController() {
		facade = new AdministracionUsuariosFacade();
	}

	@FXML
	private void login(ActionEvent event) {
		String name = usuario.getText();
		String contrasena = password.getText();

		try {
			facade.login(name, contrasena);
			regionsContainer.mostrarBarraDeMenu();
			regionsContainer.setCurrentScreen(MainApp.PANTALLA_ADMINISTRACION_USUARIOS);
			limpiarComponentes();
		} catch (RuntimeException e) {
			Alert alert = buildDialog(AlertType.ERROR, e.getMessage(), null);
			alert.showAndWait();
			e.printStackTrace();
		}
	}

	private Alert buildDialog(AlertType alertType, String contentText, String headerText) {
		Alert alert = new Alert(alertType);
		alert.setTitle(alertType.name() + " message");
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);
		return alert;
	}

	private void limpiarComponentes() {
		usuario.clear();
		password.clear();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// Dynamic initialization of controls (if any)
	}

	@Override
	public void setContainer(RegionsContainer container) {
		this.regionsContainer = container;
	}
}