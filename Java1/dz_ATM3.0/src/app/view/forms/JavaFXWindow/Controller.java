package app.view.forms.JavaFXWindow;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

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
        Main main = Main.getInstance();
        System.err.println(main);
        main.setUpperScreenAnchor(upperScreenAnchor);
        main.setLowerScreenAnchor(lowerScreenAnchor);
    }

    @FXML
    void action() {
        System.err.println("action");
    }
}