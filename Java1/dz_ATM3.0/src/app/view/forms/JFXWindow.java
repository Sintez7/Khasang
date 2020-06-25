package app.view.forms;

import app.controller.Controller;
import app.view.View;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class JFXWindow extends Application implements View {

    private Controller controller;

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
        primaryStage.setTitle("JFXWindow");
        primaryStage.setScene(new Scene(root, 600, 800));
        primaryStage.show();
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void update() {

    }

    @Override
    public void run() {

    }

    public void myLaunch(String[] args) {
        launch(args);
    }
}
