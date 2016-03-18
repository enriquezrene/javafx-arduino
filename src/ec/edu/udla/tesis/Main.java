package ec.edu.udla.tesis;

import ec.edu.udla.tesis.ui.regions.impl.DatosArduinoController;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

	BorderPane panelContenedor = new BorderPane();
	private Node centerPanel;

	FadeTransition fadeTransition;

	@Override
	public void start(Stage primaryStage) throws Exception {
		centerPanel = new StackPane();
		fadeTransition = new FadeTransition(Duration.seconds(2), centerPanel);

		Menu menuInicio = new Menu("Inicio");
		MenuItem itemCargarDatos = crearItemDeMenu("Cargar datos", 'L');
		menuInicio.getItems().addAll(itemCargarDatos);

		MenuBar barraDeMenu = new MenuBar();
		barraDeMenu.getMenus().add(menuInicio);

		panelContenedor.setTop(barraDeMenu);
		panelContenedor.setStyle("-fx-background-color: WHITE");
		panelContenedor.setCenter(centerPanel);

		Scene scene = new Scene(panelContenedor, primaryStage.getMaxWidth(), primaryStage.getMaxHeight());
		primaryStage.setTitle("Aplicacion Demo");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	private MenuItem crearItemDeMenu(String fontName, char accKey) throws Exception {
		MenuItem menuItem = new MenuItem(fontName);
		menuItem.setAccelerator(KeyCombination.keyCombination("Ctrl+" + accKey));
		menuItem.setOnAction(event -> {
			// centerPanel = new DrawableLecturaGlucosa().getRegion();
			// fadeTransition.getNode().setOpacity(1.0);
			// fadetransition.settovalue(0);
			// fadetransition.playfromstart();

			try {
				Parent root = FXMLLoader.load(DatosArduinoController.class.getResource("DatosArduino.fxml"));
				panelContenedor.setCenter(root);
				fadeTransition.setNode(panelContenedor);
				fadeTransition.getNode().setOpacity(0.1);
				fadeTransition.setToValue(1.0);
				fadeTransition.playFromStart();
			} catch (Exception e) {
				e.printStackTrace();
			}

		});
		return menuItem;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
