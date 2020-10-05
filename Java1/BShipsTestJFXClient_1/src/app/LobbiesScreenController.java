package app;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import app.shared.LobbyData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import app.shared.Lobby;

public class LobbiesScreenController {

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
        assert list != null : "fx:id=\"list\" was not injected: check your FXML file 'lobby.fxml'.";

        list.setCellFactory(new Callback<ListView<Lobby>, ListCell<Lobby>>() {
            @Override
            public ListCell<Lobby> call(ListView<Lobby> lobbyListView) {
                return new LobbyCell();
            }
        });

        list.setItems(lobbiesList);
//        list.getSelectionModel().selectFirst();
    }

    @FXML
    void selectLobby(ActionEvent event) {
        main.selectLobby(selectedLobby);
    }

    public void addLobbies(List<LobbyData> dPackage) {
        lobbiesList.clear();
        for (LobbyData lobbyData : dPackage) {
            lobbiesList.add(new Lobby(lobbyData.lobbyName()));
        }
//        lobbiesList.add(new Lobby());
//        System.err.println(lobbiesList);
//        lobbiesList.add(new Lobby());
//        System.err.println(lobbiesList);
//        lobbiesList.add(new Lobby());
//        System.err.println(lobbiesList);
    }

    private class LobbyCell extends ListCell<Lobby> {
//        private boolean inited = true;
        @Override
        synchronized protected void updateItem(Lobby lobby, boolean b) {
            super.updateItem(lobby, b);
            if (lobby != null) {
                setText(lobby.getName());
                selectedLobby = lobby;
            } else {
                setText("");
            }
        }
    }
}
