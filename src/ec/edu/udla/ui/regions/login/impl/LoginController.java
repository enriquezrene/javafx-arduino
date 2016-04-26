package ec.edu.udla.ui.regions.login.impl;

import java.net.URL;
import java.util.ResourceBundle;

import ec.edu.udla.facade.AdministracionUsuariosFacade;
import ec.edu.udla.ui.MainApp;
import ec.edu.udla.ui.regions.AbstractController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController extends AbstractController implements Initializable {


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
        try {
            facade.login(usuario.getText(), password.getText());
            container.mostrarBarraDeMenu();
            container.setCurrentScreen(MainApp.PANTALLA_INGRESO_DATOS);
            limpiarTextInputs(usuario, password);
        } catch (RuntimeException e) {
            Alert alert = buildDialog(AlertType.ERROR, e.getMessage(), null);
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    private Alert buildDialog(AlertType alertType, String contentText, String headerText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(alertType.name());
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        return alert;
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Dynamic initialization of controls (if any)
    }

}