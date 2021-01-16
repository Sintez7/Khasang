// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++ and C#: http://www.viva64.com
package app;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import app.shared.LobbyData;
import app.shared.LobbyRoomData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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

    @FXML
    private Label lobbyNameLabel;

    @FXML
    private Button vsAIbtn;


    private Main main;
    public RoomController(Main main) {
        this.main = main;
    }

    @FXML
    void startGame(ActionEvent event) {
        main.sendStartGame();
    }

    @FXML
    void leaveRoom(ActionEvent event) {
        main.leaveRoom();
    }

    @FXML
    void playAgainstAI(ActionEvent event) {
        main.playAgainstAI();
    }

    public void receiveRoomInfo(LobbyRoomData data) {
        player1Name.setText(data.getPlayer1Name());
        player2Name.setText(data.getPlayer2Name());
        lobbyNameLabel.setText(data.getRoomName());
    }

    @FXML
    void initialize() {
        assert player1Name != null : "fx:id=\"player1Name\" was not injected: check your FXML file 'room.fxml'.";
        assert player2Name != null : "fx:id=\"player2Name\" was not injected: check your FXML file 'room.fxml'.";

    }
}
