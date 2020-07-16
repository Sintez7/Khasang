package app.view.forms.JavaFXWindow;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class WithdrawalScreenController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label textLabel;

    @FXML
    void add100(ActionEvent event) {

    }

    @FXML
    void add1000(ActionEvent event) {

    }

    @FXML
    void add50(ActionEvent event) {

    }

    @FXML
    void add500(ActionEvent event) {

    }

    @FXML
    void initialize() {
        Service.addWSController(this);
    }
}
