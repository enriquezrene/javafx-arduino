package ec.edu.udla.ui;

import ec.edu.udla.ui.regions.RegionsContainer;
import ec.edu.udla.ui.regions.impl.IngresarPacienteController;
import ec.edu.udla.ui.regions.lectorarduino.impl.LeerInformacionArduinoController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class MainApp extends Application {

	private static final String PANTALLA_INGRESO_DATOS = "IngresarPaciente.fxml";
	private static final String PANTALLA_LEER_INFORMACION = "LeerInformacionArduino.fxml";

	private RegionsContainer mainContainer;

	@Override
	public void start(Stage primaryStage) throws Exception {

		mainContainer = new RegionsContainer();
		mainContainer.loadScreen(PANTALLA_INGRESO_DATOS,
				IngresarPacienteController.class.getResource(PANTALLA_INGRESO_DATOS));
		mainContainer.loadScreen(PANTALLA_LEER_INFORMACION,
				LeerInformacionArduinoController.class.getResource(PANTALLA_LEER_INFORMACION));

		mainContainer.setCurrentScreen(PANTALLA_INGRESO_DATOS);
		mainContainer.setId("main_container");

		Menu menuInicio = new Menu("Inicio");
		MenuItem optionShowScreenOne = crearItemDeMenu("Ingresar paciente", PANTALLA_INGRESO_DATOS);
		MenuItem opcionLeerInformacionArduino = crearItemDeMenu("Establecer conexion", PANTALLA_LEER_INFORMACION);
		menuInicio.getItems().addAll(optionShowScreenOne, opcionLeerInformacionArduino);

		MenuBar barraDeMenu = new MenuBar();
		barraDeMenu.getMenus().add(menuInicio);

		mainContainer.setTop(barraDeMenu);

		Scene scene = new Scene(mainContainer, primaryStage.getMaxWidth(), primaryStage.getMaxHeight());

		scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());

		primaryStage.setTitle("Aplicacion Demo");
		primaryStage.setScene(scene);
		primaryStage.show();

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
