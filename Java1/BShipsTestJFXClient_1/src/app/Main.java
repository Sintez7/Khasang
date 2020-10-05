package app;

import app.shared.DataPackage;
import app.shared.Lobby;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private AnchorPane anchor;
    Controller controller;
    LobbiesScreenController lsController;
    ConnectionHandler handler;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("connectionScreen.fxml"));
        FXMLLoader loader = new FXMLLoader();
        controller = new Controller(this);
        loader.setLocation(getClass().getResource("connectionScreen.fxml"));
        loader.setController(controller);
        loader.load();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(loader.getRoot(), 400, 800));
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
        handler = new ConnectionHandler(lsController);
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

    public void selectLobby(Lobby selectedLobby) {
        handler.sendData
    }
}
