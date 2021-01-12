// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++ and C#: http://www.viva64.com
package app;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class Controller {

    private Main main;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane anchor;

    @FXML
    private TextField nameTextField;

    public Controller() {
//        System.err.println("controller default constructor");
//        anchor = new AnchorPane();
//        Main.setAnchor(anchor);
    }

    public Controller(Main main) {
        this.main = main;
    }

    @FXML
    void initialize() {
        main.setAnchor(anchor);
    }

    @FXML
    void connect(ActionEvent event) {
        main.loadLobby(nameTextField.getText());
    }
}
