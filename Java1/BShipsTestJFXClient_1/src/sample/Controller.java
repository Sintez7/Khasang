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
    private volatile ListView<Lobby> list;
    private volatile ObservableList<Lobby> lobbiesList = FXCollections.observableArrayList();

    @FXML
    private AnchorPane anchor;

    public Controller(Main main) {
        this.main = main;
        anchor = new AnchorPane();
        main.setAnchor(anchor);
        list = new ListView<>();
    }

    @FXML
    void initialize() {

    }

    @FXML
    void connect(ActionEvent event) {

        try {
            socket = new Socket("localhost", 2111);
            main.loadLobbyScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }

        list.setCellFactory(new Callback<ListView<Lobby>, ListCell<Lobby>>() {
            @Override
            public ListCell<Lobby> call(ListView<Lobby> lobbyListView) {
                return new LobbyCell();
            }
        });

        list.setItems(lobbiesList);
        list.getSelectionModel().selectFirst();

        OutConnectHandler outCH = new OutConnectHandler(socket);
        outCH.setDaemon(true);
        outCH.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        InConnectionHandler inCH = new InConnectionHandler(socket, this);
        inCH.setDaemon(true);
        inCH.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.err.println("OutCH state: " + outCH.getState());
        System.err.println("InCH state : " + inCH.getState());
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
        private Controller controller;

        ObjectInputStream in;

        public InConnectionHandler(Socket socket, Controller controller) {
            this.socket = socket;
            this.controller = controller;
        }

        @Override
        public void run() {
            try {
                in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
                while(true) {
                    try {
//                        LobbiesPackage lp = (LobbiesDataPackage) in.readObject();
//                        controller.refreshLobbiesList(lp);
                        int input = in.read();
                        System.err.println("echo: " + input);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void refreshLobbiesList(LobbiesPackage lp) {
        lobbiesList.clear();
        for (LobbyData ld : lp.getList()) {
            lobbiesList.add(new Lobby(ld));
        }
    }
}
