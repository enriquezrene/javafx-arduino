package ec.edu.udla.ui.regions.help;

import ec.edu.udla.ui.regions.AbstractController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by rene on 20/05/16.
 */
public class HelpController extends AbstractController implements Initializable {

    @FXML
    private WebView myWebView;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        WebEngine engine = myWebView.getEngine();
        String url = getClass().getResource("help.html").toExternalForm();
        engine.load(url);
    }
}
