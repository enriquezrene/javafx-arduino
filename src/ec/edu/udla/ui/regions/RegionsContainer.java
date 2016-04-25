package ec.edu.udla.ui.regions;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;

public class RegionsContainer extends BorderPane {

	private HashMap<String, Node> screens;
	private MenuBar barraDeMenu;

	public RegionsContainer() {
		super();
		screens = new HashMap<>();
		barraDeMenu = new MenuBar();
	}

	public void mostrarBarraDeMenu() {
		this.setTop(barraDeMenu);
	}

	public void cargarOpcionesDeMenu(Menu menu) {
		this.barraDeMenu.getMenus().add(menu);
	}

	// Add the screen to the collection
	public void addScreen(String name, Node screen) {
		screens.put(name, screen);
	}

	// Returns the Node with the appropriate name
	public Node getScreen(String name) {
		return screens.get(name);
	}

	public void loadScreen(String name, URL urlResource) throws IOException {
		FXMLLoader myLoader = new FXMLLoader(urlResource);
		Parent screenToLoad = myLoader.load();
		DrawableRegion drawableRegion = myLoader.getController();
		drawableRegion.setContainer(this);
		addScreen(name, screenToLoad);
	}

	public void setCurrentScreen(final String name) {
		this.setCenter(screens.get(name));
	}

	// This method will remove the screen with the given name from the
	// collection of screens
	public void unloadScreen(String name) {
		screens.remove(name);
	}

}
