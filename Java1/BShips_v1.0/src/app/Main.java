package app;

import app.controller.Controller;
import app.model.Field;
import app.model.Model;
import app.player.AI;
import app.player.Human;
import app.player.Player;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        Controller controller = new Controller(this);
        initModules(controller);
        loader.setLocation(getClass().getResource("view/sample.fxml"));
        loader.setController(controller);
        Parent root = loader.load();
        primaryStage.setTitle("Battle Ships The Game");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    private void initModules(Controller controller) {
        Model model = new Model(this);
        model.setPlayerOne(new Human())
             .setPlayerTwo(new AI())
             .setPlayerOneField(new Field())
             .setPlayerTwoField(new Field());

        model.setController(controller);
        controller.setModel(model);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
