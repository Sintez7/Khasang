package app;

import app.shared.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/*
 * Главный исполняемый класс, представляет собой модель
 * View представлен .fxml файлами
 * Controller меняется в зависимости от текущего View
 */

public class Main extends Application {

    private AnchorPane anchor;
    Controller controller;
    LobbiesScreenController lsController;
    ConnectionHandler handler;
    volatile RoomController roomController;
    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("connectionScreen.fxml"));
        this.primaryStage = primaryStage;
        FXMLLoader loader = new FXMLLoader();
        controller = new Controller(this);
        loader.setLocation(getClass().getResource("connectionScreen.fxml"));
        loader.setController(controller);
        loader.load();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(loader.getRoot(), 600, 600));
        primaryStage.show();
    }

    public synchronized void setAnchor(AnchorPane pane) {
        anchor = pane;
    }

    public void loadLobby(String name) {
        System.err.println("screen loading");
        FXMLLoader screenLoader = new FXMLLoader();
        if (screenLoader.getRoot() != anchor) {
            screenLoader.setRoot(anchor);
        }
        screenLoader.setLocation(getClass().getResource("lobby.fxml"));
        lsController = new LobbiesScreenController(this);
        handler = new ConnectionHandler(lsController, this);
        handler.setDaemon(true);
        handler.start();
        screenLoader.setController(lsController);
        anchor.getChildren().clear();
        try {
            screenLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("tried to load: lobby.fxml");
        }
        System.err.println("loaded");
    }

    public void loadRoom() {
        System.err.println("screen loading");
        FXMLLoader screenLoader = new FXMLLoader();
        if (screenLoader.getRoot() != anchor) {
            screenLoader.setRoot(anchor);
        }
        screenLoader.setLocation(getClass().getResource("room.fxml"));
        roomController = new RoomController(this);
        screenLoader.setController(roomController);
        anchor.getChildren().clear();
        try {
            screenLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("tried to load: lobby.fxml");
        }
        System.err.println("loaded");
    }

    public void selectLobby(Lobby selectedLobby) {
        handler.sendData(new LobbyChoice(selectedLobby.getId()));
        loadRoom();
    }

    public void handleLobbiesPackage (LobbiesDataPackage data) {
        System.err.println("received LobbiesDataPackage");
        lsController.addLobbies(data.getList());
    }

    public void handleRoomPackage (LobbyRoomData data) {
        System.err.println("received LobbyRoomData");
        if (roomController != null) {
            roomController.receiveRoomInfo(data);
        } else {
            System.err.println("roomController is null");
        }
    }

    public void sendStartGame() {
        handler.sendData(new GameStart());
    }

    public void handleGameStart() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("game.fxml"));
            Scene gameScene = new Scene(root, 800, 800);
            primaryStage.setScene(gameScene);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
