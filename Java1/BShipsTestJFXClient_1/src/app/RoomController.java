package app;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import app.shared.LobbyData;
import app.shared.LobbyRoomData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class RoomController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label player1Name;

    @FXML
    private Label player2Name;

    private Main main;
    public RoomController(Main main) {
        this.main = main;
    }

    @FXML
    void startGame(ActionEvent event) {

    }

    @FXML
    void leaveRoom(ActionEvent event) {

    }

    public void receiveRoomInfo(LobbyRoomData data) {
        player1Name.setText(data.getPlayer1Name());
        player2Name.setText(data.getPlayer2Name());
    }

    @FXML
    void initialize() {
        assert player1Name != null : "fx:id=\"player1Name\" was not injected: check your FXML file 'room.fxml'.";
        assert player2Name != null : "fx:id=\"player2Name\" was not injected: check your FXML file 'room.fxml'.";

    }
}
