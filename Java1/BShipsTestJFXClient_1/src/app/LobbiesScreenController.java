package app;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import app.shared.Lobby;
import app.shared.LobbyData;
import app.shared.LobbyRoomData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class LobbiesScreenController implements Controllable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private volatile ListView<Lobby> list;
    private volatile ObservableList<Lobby> lobbiesList = FXCollections.observableArrayList();

    private Main main;
    private Lobby selectedLobby;

    public LobbiesScreenController(Main main) {
        this.main = main;
    }

    @FXML
    void initialize() {
        list.setCellFactory(new Callback<ListView<Lobby>, ListCell<Lobby>>() {
            @Override
            public ListCell<Lobby> call(ListView<Lobby> lobbyListView) {
                return new LobbyCell();
            }
        });

        list.setItems(lobbiesList);
    }

    @FXML
    void selectLobby(ActionEvent event) {
        main.selectLobby(selectedLobby);
    }

    @FXML
    void createLobby(ActionEvent event) {
        main.createLobby();
    }

    @Override
    public void addLobbies(List<LobbyData> dPackage) {
        lobbiesList.clear();
        for (LobbyData lobbyData : dPackage) {
            lobbiesList.add(new Lobby(lobbyData));
        }
    }

    @Override
    public void receiveRoomInfo(LobbyRoomData data) {
        System.err.println("receiveRoomData in LobbiesScreenController");
    }

    private class LobbyCell extends ListCell<Lobby> {
        @Override
        synchronized protected void updateItem(Lobby lobby, boolean b) {
            super.updateItem(lobby, b);
            if (lobby != null) {
                setText(lobby.getName() + " " + lobby.getId());
                selectedLobby = lobby;
            } else {
                setText("");
            }
        }
    }
}
