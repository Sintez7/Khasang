package app.view.forms.JavaFXWindow;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import app.view.forms.JavaFXWindow.ATMMainWindow;


public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane root;

    @FXML
    private AnchorPane upperScreenAnchor;

    @FXML
    private AnchorPane lowerScreenAnchor;

    @FXML
    void initialize() {
        ATMMainWindow ATMMainWindow = app.view.forms.JavaFXWindow.ATMMainWindow.getInstance();
        System.err.println(ATMMainWindow);
        ATMMainWindow.setUpperScreenAnchor(upperScreenAnchor);
        ATMMainWindow.setLowerScreenAnchor(lowerScreenAnchor);
    }

    @FXML
    void action() {
        System.err.println("action");
    }
}