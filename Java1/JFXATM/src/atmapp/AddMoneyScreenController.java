package atmapp;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class AddMoneyScreenController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

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
        Service.setAddMoneyScreenController(this);
    }
}
