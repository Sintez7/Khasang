package app.view.forms;

import app.App;
import app.controller.Controller;
import app.model.ModelData;
import app.model.bank.card.ICard;
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

import static app.App.args;

public class JFXWindow extends Application implements View {

    public static final Object viewMonitor = new Object();

    static private JFXWindowController windowController;
    private Controller controller;

    @Override
    public void start(Stage primaryStage) throws IOException {
//        launch();
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
//        synchronized (App.monitor) {
//            App.monitor.notifyAll();
//        }
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

    @Override
    public void addCard(ICard card) {
        windowController.addCard(card);
    }

    private void updateText(ModelData data) {
        windowController.updateText(data.getBankResponse().getMessage() + data.getMessage());
    }

    @Override
    public void run() {
        launch(args);
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
