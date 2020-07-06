package app.view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class View extends Application implements ViewComponent {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane root;

    @FXML
    void initialize() {

    }

    @Override
    public void start(Stage stage) throws Exception {
        System.err.println("View class " + Thread.currentThread());
        System.err.println("View_currentActiveThreads: " + Thread.activeCount());
    }
}
