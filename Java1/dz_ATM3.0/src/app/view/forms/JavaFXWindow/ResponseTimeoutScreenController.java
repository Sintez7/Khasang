package app.view.forms.JavaFXWindow;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ResponseTimeoutScreenController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label textLabel;

    @FXML
    void initialize() {
        assert textLabel != null : "fx:id=\"textLabel\" was not injected: check your FXML file 'responseTimeoutScreen.fxml'.";

    }
}
