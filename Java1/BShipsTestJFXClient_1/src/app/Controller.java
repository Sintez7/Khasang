package app;

import java.net.Socket;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import app.shared.LobbyData;
import app.shared.LobbyRoomData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import app.shared.Lobby;

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
