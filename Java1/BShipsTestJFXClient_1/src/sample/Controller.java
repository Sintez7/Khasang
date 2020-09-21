package sample;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

public class Controller {

    private Socket socket;
    private Main main;
    private Lobby selectedLobby;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<Lobby> list;
    private ObservableList<Lobby> lobbiesList = FXCollections.observableArrayList();

    @FXML
    private AnchorPane anchor;

    public Controller(Main main) {
        this.main = main;
        anchor = new AnchorPane();
        main.setAnchor(anchor);
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
        list.getSelectionModel().selectFirst();
    }

    @FXML
    void connect(ActionEvent event) {
        try {
            socket = new Socket("localhost", 2111);
            main.loadLobbyScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class LobbyCell extends ListCell<Lobby> {
        @Override
        synchronized protected void updateItem(Lobby lobby, boolean b) {
            super.updateItem(lobby, b);

            if (lobby != null) {
                setText(lobby.getName());
                selectedLobby = lobby;
            }
        }
    }

    private static class OutConnectHandler extends Thread {
        private Socket socket;
        private ObjectOutputStream out;

        public OutConnectHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                out = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static class InConnectionHandler extends Thread{
        private Socket socket;

        ObjectInputStream in;
        public InConnectionHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
