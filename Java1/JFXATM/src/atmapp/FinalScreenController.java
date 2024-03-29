package atmapp;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class FinalScreenController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label textLabel;

    private boolean continueWork;

    @FXML
    void continueWork(ActionEvent event) {
        continueWork = true;
        callNext();
    }

    @FXML
    void finishWork(ActionEvent event) {
        continueWork = false;
        callNext();
    }

    private void callNext() {
        Service.callNext();
    }

    public boolean getContinue() {
        return continueWork;
    }

    @FXML
    void initialize() {
        Service.setFSController(this);
    }
}
