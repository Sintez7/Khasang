package app;

import app.shared.*;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
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
    private GameController gameController;
//    private RightClickHandler rcHandler;

    public final Object LOADED_MONITOR = new Object();

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
        primaryStage.setTitle("JFX_SeaBattle_v0.1");
        Scene scene = new Scene(loader.getRoot(), 600, 600);
        scene.getStylesheets().add(0, "app/styles/style.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public synchronized void setAnchor(AnchorPane pane) {
        anchor = pane;
    }

    public void loadLobby() {
        System.err.println("screen loading");
        FXMLLoader screenLoader = new FXMLLoader();
        if (screenLoader.getRoot() != anchor) {
            screenLoader.setRoot(anchor);
        }
        screenLoader.setLocation(getClass().getResource("lobby.fxml"));
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
        synchronized (LOADED_MONITOR) {
            try {
                LOADED_MONITOR.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        handler.sendData(new PlayerName(name));
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

    public void createLobby() {
        handler.sendData(new CreateLobby());
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
        System.err.println("screen loading");
        FXMLLoader screenLoader = new FXMLLoader();
        if (screenLoader.getRoot() != anchor) {
            screenLoader.setRoot(anchor);
        }
//        anchor.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent mouseEvent) {
//                System.err.println("Main handle right click event");
//                if (mouseEvent.getButton() == MouseButton.SECONDARY) {
//                    gameController.handleRightClick();
//                }
//            }
//        });
        screenLoader.setLocation(getClass().getResource("game.fxml"));
        gameController = new GameController(this);
        screenLoader.setController(gameController);
        anchor.getChildren().clear();
        try {
            screenLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("tried to load: lobby.fxml");
        }
        System.err.println("loaded");
    }

    public void handleHitResponse(HitResponse temp) {
        gameController.handleHitResponse(temp);
    }

    public void handleTurnUpdate(TurnUpdate temp) {
        gameController.handleTurnUpdate(temp);
    }

    public void leaveRoom() {
        handler.sendData(new LeaveRoom());
    }

    public void handlePlaceShip(int x, int y, int shipSize, int shipBias) {
        handler.sendData(new PlaceShip(x, y, shipSize, shipBias));
    }

    public void handlePlaceShipResponse(PlaceShipResponse response) {
        gameController.handlePlaceShipResponse(response.getResponse());
    }

    public void handleShoot(int x, int y) {
        handler.sendData(new Hit(x, y));
    }
}
