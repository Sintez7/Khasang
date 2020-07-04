package app.view.forms;

import app.App;
import app.controller.Controller;
import app.controller.exceptions.AtmIsBusyException;
import app.controller.exceptions.CardBusyException;
import app.model.MenuOption;
import app.model.ModelData;
import app.model.bank.card.ICard;
import app.view.View;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.List;

import static app.App.args;

public class JFXWindow extends Application implements View {

    public static final Object viewMonitor = new Object();

    static private JFXWindowController windowController;
    static private MainMenuComponentController menuController;
    private volatile Controller controller;
    private MenuOption selectedOption;

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
    public synchronized void setController(Controller controller) {
        this.controller = controller;
    }

    public static void setWindowController(JFXWindowController controller) {
        windowController = controller;
    }

    public static void setMenuController(MainMenuComponentController controller) {
        menuController = controller;
    }

    @Override
    public void update(List<MenuOption> options) {
        if (options != null) {
            showOptions(options);
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

    public String ejectCard() {

        String result = "";

        if (controller == null) {
            controller = App.getController();
        }

        try {
            if (controller.ejectCard()) {
                result = "Card successfully ejected";
            } else {
                result = "Card slot is already empty";
            }
        } catch (CardBusyException e) {
            result = "Card is busy, please wait";
        }
        return result;
    }

    public String insertCard(ICard card) {

        String result = "";

        if (controller == null) {
            controller = App.getController();
        }

        try {
            if (controller.insertCard(card)) {
                result = "Card\n" + card.toString() + "\ninserted";
            } else {
                result = "ATM is already occupied with another card";
            }
        } catch (AtmIsBusyException e) {
            result = "ATM is busy, please wait";
        }
        return result;
    }

    public void loadCenterComponent(AnchorPane centerScreenAnchor, String component) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(component));
            FXMLLoader loader = new FXMLLoader(getClass().getResource(component));
            loader.setRoot(centerScreenAnchor);
            AnchorPane newPane = loader.load();
//            AnchorPane newPane2 = root;

            // Set the loaded FXML file as the content of our main right-side pane
            centerScreenAnchor.getChildren().setAll(root);

            // Reset the anchors
//            AnchorPane.setBottomAnchor(newPane, 0.0);
//            AnchorPane.setLeftAnchor(newPane, 0.0);
//            AnchorPane.setRightAnchor(newPane, 0.0);
//            AnchorPane.setTopAnchor(newPane, 0.0);

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (controller == null) {
            controller = App.getController();
        }

        showOptions(controller.getMenuOptions());
    }

    private void showOptions(List<MenuOption> options) {
        windowController.okBtnToSelectOption();
        if (menuController == null) {
            menuController = MainMenuComponentController.self;
        }
        for (MenuOption option : controller.getMenuOptions()) {
            menuController.addMenuOption(option);
        }
    }

    public void chooseOption() {
        controller.confirmMenuOptionSelect(selectedOption);
    }

    public void setSelectedOption(MenuOption option) {
        selectedOption = option;
    }
}
