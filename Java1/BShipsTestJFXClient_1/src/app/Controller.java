package app;

import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import app.shared.Lobby;

public class Controller {

    private Socket socket;
    private Main main;
    private Lobby selectedLobby;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane anchor;

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
//        anchor = new AnchorPane();
        main.setAnchor(anchor);
    }

    @FXML
    void connect(ActionEvent event) {
        main.loadLobby("sdasdas");
    }
}
