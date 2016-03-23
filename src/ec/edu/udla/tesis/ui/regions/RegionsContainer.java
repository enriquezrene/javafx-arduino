package ec.edu.udla.tesis.ui.regions;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import com.sun.glass.ui.Accessible.EventHandler;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

public class RegionsContainer extends BorderPane {

	private HashMap<String, Node> screens;

	public RegionsContainer() {
		super();
		screens = new HashMap<>();
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

	//
	// // This method tries to displayed the screen with a predefined name.
	// // First it makes sure the screen has been already loaded. Then if there
	// is
	// // more than
	// // one screen the new screen is been added second, and then the current
	// // screen is removed.
	// // If there isn't any screen being displayed, the new screen is just
	// added
	// // to the root.
	public boolean setScreen(final String name) {
	 if (screens.get(name) != null) { // screen loaded
	 final DoubleProperty opacity = opacityProperty();

	 if (!getChildren().isEmpty()) { // if there is more than one screen
	 Timeline fade = new Timeline(new KeyFrame(Duration.ZERO, new
	 KeyValue(opacity, 1.0)),
	 new KeyFrame(new Duration(1000), new EventHandler<ActionEvent>() {
	 @Override
	 public void handle(ActionEvent t) {
	 getChildren().remove(0); // remove the displayed
	 // screen
	 getChildren().add(0, screens.get(name)); // add
	 // the
	 // screen
	 Timeline fadeIn = new Timeline(new KeyFrame(Duration.ZERO, new
	 KeyValue(opacity, 0.0)),
	 new KeyFrame(new Duration(800), new KeyValue(opacity, 1.0)));
	 fadeIn.play();
	 }
	 }, new KeyValue(opacity, 0.0)));
	 fade.play();

	 } else {
	 setOpacity(0.0);
	 getChildren().add(screens.get(name)); // no one else been
	 // displayed, then just
	 // show
	 Timeline fadeIn = new Timeline(new KeyFrame(Duration.ZERO, new
	 KeyValue(opacity, 0.0)),
	 new KeyFrame(new Duration(2500), new KeyValue(opacity, 1.0)));
	 fadeIn.play();
	 }
	 return true;
	 } else {
	 System.out.println("screen hasn't been loaded!!! \n");
	 return false;
	 }
	//
	// /*
	// * Node screenToRemove; if(screens.get(name) != null){ //screen loaded
	// * if(!getChildren().isEmpty()){ //if there is more than one screen
	// * getChildren().add(0, screens.get(name)); //add the screen
	// * screenToRemove = getChildren().get(1); getChildren().remove(1);
	// * //remove the displayed screen }else{
	// * getChildren().add(screens.get(name)); //no one else been displayed,
	// * then just show } return true; }else { System.out.println(
	// * "screen hasn't been loaded!!! \n"); return false; }
	// */
	// }
	//
	// // This method will remove the screen with the given name from the
	// // collection of screens
	// public boolean unloadScreen(String name) {
	// if (screens.remove(name) == null) {
	// System.out.println("Screen didn't exist");
	// return false;
	// } else {
	// return true;
	// }
	// }

}
