package atmapp;

import atmapp.model.card.Card;
import atmapp.model.card.CreditCard;
import atmapp.model.card.DebitCard;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class Main extends Application {

    private static Main main;

    private AnchorPane upperScreenAnchor;
    private AnchorPane lowerScreenAnchor;
    private StateMachine sm;

    public static void main(String[] args) {
        launch(args);
    }

    public Main() {
        main = this;
        Service.setMain(this);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("mainframe.fxml"));
        primaryStage.setTitle("JFXATM");
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
        primaryStage.setScene(new Scene(root, 400, 800));
        sm = new StateMachine();
        primaryStage.show();
    }

    private void loadUpperScreen(String s) {
        System.err.println("upper screen loading");
        FXMLLoader upperScreenLoader = new FXMLLoader();
        if (upperScreenLoader.getRoot() != upperScreenAnchor) {
            upperScreenLoader.setRoot(upperScreenAnchor);
        }
        upperScreenLoader.setLocation(getClass().getResource(s));
        upperScreenAnchor.getChildren().clear();
        try {
            upperScreenLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.err.println("loaded");
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

//TODO: Это всё надо в стейт-машину засунуть

    public void next() {
        sm.execute();
    }

    private class StateMachine {

        private State currentState = null;


        StateMachine() {
            currentState = new Starting();
            execute();
        }

        synchronized public void execute() {
//            Service.getKeyboardAdapter().disableControls();
            currentState = currentState.execute();
        }
    }

    private class State {

        protected State execute() {
            System.err.println("Default execute method!");
            return null;
        }
    }

    private class Starting extends State {
        @Override
        protected State execute() {
            loadKeyboard();
            System.err.println("keyboard loaded");
            loadUpperScreen("welcomeScreen.fxml");
            System.err.println("welcomeScreen loaded");
            Service.keyboardAdapter.disableControls();
            Service.callStarted();
            return new Welcome();
        }
    }

    private class Welcome extends State {
        @Override
        protected State execute() {
            loadUpperScreen("cardSelectionScreen.fxml");
            Service.getKeyboardAdapter().setActualController(new KeyboardCardSelect());
            Service.getCardSSController().addCard(new DebitCard());
            Service.getCardSSController().addCard(new CreditCard());
            Service.keyboardAdapter.enableControls();
            return new CardSelection();
        }
    }

    private class CardSelection extends State {
        @Override
        protected State execute() {
            Service.keyboardAdapter.disableControls();
            // здесь должен быть код для АТМа, на принятие карты

            loadUpperScreen("mainMenuScreen.fxml");

            return new MainMenu();
        }
    }

    private class MainMenu extends State {
        @Override
        protected State execute() {
            switch (Service.getMMSController().getSelected()) {
                case ADD_MONEY:
                    return addMoney();
                case SHOW_CREDIT:
                    return new ShowCredit();
                case BALANCE:
                    return new Balance();
                case EJECT_CARD:
                    return new EjectCard();
                case WITHDRAWAL:
                    return new Withdrawal();
                case SHOW_HISTORY:
                    return new ShowHistory();
                default:
                    System.err.println("Unknown Selection");
                    return null;
            }
        }

        private State addMoney() {
            loadUpperScreen("addMoneyScreen");
            Service.getKeyboardAdapter().setActualController(new KeyboardAddMoney());
            return new AddMoney();
        }
    }

    private class AddMoney extends State {
        @Override
        protected State execute() {


            // операции по добавлению денег на счёт пользователя

            return new FinalScreen();
        }
    }
}
