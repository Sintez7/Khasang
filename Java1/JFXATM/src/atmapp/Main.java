package atmapp;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static Main main;

    private AnchorPane upperScreenAnchor;
    private AnchorPane lowerScreenAnchor;

    public static void main(String[] args) {
        launch(args);
    }

    public Main() {
        main = this;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("mainframe.fxml"));
        primaryStage.setTitle("JFXATM");
        primaryStage.setScene(new Scene(root, 400, 800));
        loadKeyboard();
        loadUpperScreen("welcomeScreen.fxml");
        primaryStage.show();


//        Parent temp = FXMLLoader.load(getClass().getResource("keyboard.fxml"));
//        lowerScreenAnchor.getChildren().setAll(temp);

    }

    private void loadUpperScreen(String s) {
        FXMLLoader upperScreenLoader = new FXMLLoader();
        upperScreenLoader.setRoot(upperScreenAnchor);
        upperScreenLoader.setLocation(getClass().getResource(s));
        upperScreenAnchor.getChildren().clear();
        try {
            upperScreenLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadKeyboard() {
        FXMLLoader lowerScreenLoader = new FXMLLoader();
        lowerScreenLoader.setRoot(lowerScreenAnchor);
        lowerScreenLoader.setLocation(getClass().getResource("keyboard.fxml"));
        lowerScreenAnchor.getChildren().clear();
        try {
            lowerScreenLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Main getInstance() {
        if (main != null) {
            return main;
        } else {
            System.err.println("main is null");
            return null;
        }
    }

    public void setUpperScreenAnchor(AnchorPane pane) {
        upperScreenAnchor = pane;
    }

    public void setLowerScreenAnchor(AnchorPane pane) {
        lowerScreenAnchor = pane;
    }

}
