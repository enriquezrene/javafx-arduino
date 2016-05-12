package ec.edu.udla.ui.regions.login;

import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

import ec.edu.udla.facade.AdministracionUsuariosFacade;
import ec.edu.udla.ui.MainApp;
import ec.edu.udla.ui.regions.AbstractController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
            container.setCurrentScreen(MainApp.PANTALLA_CONEXION_ARDUINO);
            limpiarTextInputs(usuario, password);
            container.mostrarBarraDeMenu();

            container.getPrimaryStage().hide();
            Stage stage = new Stage();
            container.getPrimaryStage().getScene().setFill(Color.WHITE);
            stage.setScene(container.getPrimaryStage().getScene());
            stage.setMaximized(true);
            stage.show();
            container.setPrimaryStage(stage);
        } catch (RuntimeException e) {
            Alert alert = buildDialog(AlertType.ERROR, e.getMessage(), null);
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    public static Alert buildDialog(AlertType alertType, String contentText, String headerText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(alertType.name());
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        return alert;
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}