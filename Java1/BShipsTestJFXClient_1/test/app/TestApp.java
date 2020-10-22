package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TestApp extends Application {

    public static void callLaunch() {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("game.fxml"));
        loader.setController(new GameController(null));
        loader.setRoot(new AnchorPane());
        loader.load();
        primaryStage.setTitle("JFX_SeaBattle_v0.1");
        Scene scene = new Scene(loader.getRoot(), 600, 600);
        scene.getStylesheets().add(0, "app/styles/style.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
