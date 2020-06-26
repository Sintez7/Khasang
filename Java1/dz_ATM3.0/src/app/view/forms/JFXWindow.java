package app.view.forms;

import app.controller.Controller;
import app.model.ModelData;
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

    static private JFXWindowController windowController;
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
        windowController.setMainClass(this);

        primaryStage.setTitle("JFXWindow");
        primaryStage.setScene(new Scene(root, 600, 800));
        primaryStage.show();
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    public static void setWindowController(JFXWindowController controller) {
        windowController = controller;
    }

    @Override
    public void update(ModelData data) {
        if (data != null) {
            updateText(data);
        } else {

        }
    }

    private void updateText(ModelData data) {
        windowController.updateText(data.getBankResponse().getMessage() + data.getMessage());
    }

    @Override
    public void run() {

    }

    public void myLaunch(String[] args) {
        launch(args);
    }

    public void processOkBtn() {

    }

    public void processCancelBtn() {

    }

    public void processStarBtn() {

    }

    public void processFenceBtn() {

    }
}
