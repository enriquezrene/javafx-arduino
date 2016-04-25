package ec.edu.udla.ui;

import java.io.IOException;

import ec.edu.udla.ui.regions.RegionsContainer;
import ec.edu.udla.ui.regions.adminusuarios.impl.AdministrarUsuarioController;
import ec.edu.udla.ui.regions.impl.IngresarPacienteController;
import ec.edu.udla.ui.regions.lectorarduino.impl.LeerInformacionArduinoController;
import ec.edu.udla.ui.regions.login.impl.LoginController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class MainApp extends Application {

	public static final String PANTALLA_INGRESO_DATOS = "IngresarPaciente.fxml";
	public static final String PANTALLA_LEER_INFORMACION = "LeerInformacionArduino.fxml";
	public static final String PANTALLA_LOGIN = "Login.fxml";
	public static final String PANTALLA_ADMINISTRACION_USUARIOS = "AdministracionUsuarios.fxml";

	private RegionsContainer mainContainer;

	@Override
	public void start(Stage primaryStage) throws Exception {

		cargarPantallas();

		mainContainer.setCurrentScreen(PANTALLA_LOGIN);
		mainContainer.setId("main_container");

		Menu menuInicio = new Menu("Inicio");
		MenuItem opcionIngresarPacientes = crearItemDeMenu("Ingresar paciente", PANTALLA_INGRESO_DATOS);
		MenuItem opcionAdministrarUsuarios = crearItemDeMenu("Administrar usuarios", PANTALLA_ADMINISTRACION_USUARIOS);
		MenuItem opcionLeerInformacionArduino = crearItemDeMenu("Establecer conexion", PANTALLA_LEER_INFORMACION);
		menuInicio.getItems().addAll(opcionIngresarPacientes, opcionAdministrarUsuarios, opcionLeerInformacionArduino);

		mainContainer.cargarOpcionesDeMenu(menuInicio);

		Scene scene = new Scene(mainContainer);

		scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());

		primaryStage.setTitle("Aplicacion Demo");
		primaryStage.setScene(scene);
		primaryStage.setMaximized(true);
		primaryStage.show();

	}

	private void cargarPantallas() throws IOException {
		mainContainer = new RegionsContainer();
		mainContainer.loadScreen(PANTALLA_INGRESO_DATOS,
				IngresarPacienteController.class.getResource(PANTALLA_INGRESO_DATOS));
		mainContainer.loadScreen(PANTALLA_LOGIN, LoginController.class.getResource(PANTALLA_LOGIN));
		mainContainer.loadScreen(PANTALLA_LEER_INFORMACION,
				LeerInformacionArduinoController.class.getResource(PANTALLA_LEER_INFORMACION));
		mainContainer.loadScreen(PANTALLA_ADMINISTRACION_USUARIOS,
				AdministrarUsuarioController.class.getResource(PANTALLA_ADMINISTRACION_USUARIOS));
	}

	private MenuItem crearItemDeMenu(String menuItemLabel, String screenToShow) throws Exception {
		MenuItem menuItem = new MenuItem(menuItemLabel);

		menuItem.setOnAction(event -> {
			mainContainer.setCurrentScreen(screenToShow);
		});
		return menuItem;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
