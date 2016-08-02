package ec.edu.udla.ui.regions.login;

import ec.edu.udla.ui.MainApp;
import ec.edu.udla.ui.regions.AbstractController;
import javafx.event.ActionEvent;

/**
 * 
 */
public class IntroController extends AbstractController {
    public void motrarLogin(ActionEvent actionEvent) {
        container.setCurrentScreen(MainApp.PANTALLA_LOGIN);
    }
}
