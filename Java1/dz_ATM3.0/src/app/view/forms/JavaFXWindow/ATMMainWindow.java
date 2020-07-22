package app.view.forms.JavaFXWindow;

import app.User;
import app.controller.Controller;
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

public class ATMMainWindow extends Application {

    private static volatile ATMMainWindow atmMainWindow;
    private static final String CARD_SELECTION_SCREEN = "cardSelectionScreen.fxml";
    private static User user;
    private static Controller controller;

    private AnchorPane upperScreenAnchor;
    private AnchorPane lowerScreenAnchor;
    private StateMachine sm;

//    public static void main(String[] args) {
//        launch(args);
//    }

    public ATMMainWindow() {
        atmMainWindow = this;
        Service.setATMMainWindow(this);
    }

    public static ATMMainWindow getInstance() {
        if (atmMainWindow != null) {
            return atmMainWindow;
        } else {
            System.err.println("main is null, creating new");
            return new ATMMainWindow();
        }
    }

    public static void callLaunch(User u, Controller c) {
        user = u;
        controller = c;
        launch();
    }

//    synchronized public void callLaunch() {
//        launch();
//    }

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
            System.err.println("tried to load: " + s);
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

    synchronized private void loadCardSelectionScreen() {
        System.err.println("upper screen loading");
        FXMLLoader upperScreenLoader = new FXMLLoader();
        if (upperScreenLoader.getRoot() != upperScreenAnchor) {
            upperScreenLoader.setRoot(upperScreenAnchor);
        }
        CardSelectionScreenController controller = new CardSelectionScreenController();
        upperScreenLoader.setLocation(getClass().getResource(CARD_SELECTION_SCREEN));
        controller.loadUserCards(user);
        upperScreenLoader.setController(controller);
        upperScreenAnchor.getChildren().clear();
        try {
            upperScreenLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("tried to load: " + CARD_SELECTION_SCREEN);
        }
        System.err.println("loaded");
    }

    void setUpperScreenAnchor(AnchorPane pane) {
        upperScreenAnchor = pane;
    }

    void setLowerScreenAnchor(AnchorPane pane) {
        lowerScreenAnchor = pane;
    }

    synchronized public void next() {
        sm.execute();
    }

    private class StateMachine {

        private State currentState;


        StateMachine() {
            currentState = new Starting();
            execute();
        }

        synchronized public void execute() {
            currentState = currentState.execute();
            if (currentState == null) {
                System.err.println("null state, returning to CardSelectionScreen_executeMethod");
                currentState = new CardSelection();
            }
        }

        synchronized public void eject() {
            currentState = currentState.eject();
            if (currentState == null) {
                System.err.println("null state, returning to CardSelectionScreen_ejectMethod");
                currentState = new CardSelection();
            }
        }
    }

    private class State {

        protected State execute() {
            System.err.println("Default execute method!");
            return null;
        }

        protected State eject() {
            return new EjectCard();
        }
    }

    private class Starting extends State {

        public Starting() {
            loadKeyboard();
            System.err.println("keyboard loaded");
            Service.callStarted();
        }

        @Override
        protected State execute() {
            return new Welcome();
        }

        @Override
        protected State eject() {
            System.err.println("cannot eject card from welcome screen!");
            return null;
        }
    }

    private class Welcome extends State {

        public Welcome() {
            Service.keyboardAdapter.disableControls();
            loadUpperScreen("welcomeScreen.fxml");
            System.err.println("welcomeScreen loaded");
        }

        @Override
        protected State execute() {
            return new CardSelection();
        }
    }

    private class CardSelection extends State {

        public CardSelection() {
            Service.keyboardAdapter.enableControls();
            loadCardSelectionScreen();
            Service.getKeyboardAdapter().setActualController(new KeyboardCardSelect());
        }

        @Override
        protected State execute() {
            return new MainMenu();
        }
    }

    private class MainMenu extends State {

        public MainMenu() {
            loadUpperScreen("mainMenuScreen.fxml");
        }

        @Override
        protected State execute() {
            switch (Service.getMMSController().getSelected()) {
                case ADD_MONEY:
                    return new AddMoney();
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
    }

    private class AddMoney extends State {

        public AddMoney() {
            loadUpperScreen("addMoneyScreen.fxml");
            Service.getKeyboardAdapter().setActualController(new KeyboardAddMoney());
            Service.getKeyboardAdapter().enableControls();
        }

        @Override
        protected State execute() {
//            Service.getAMSController().getSum();
            // операции по добавлению денег на счёт пользователя
            return new FinalScreen();
        }
    }

    private class ShowCredit extends State {

        public ShowCredit() {
            loadUpperScreen("showCredit.fxml");
            Service.getKeyboardAdapter().setActualController(new KeyboardAddMoney());
            Service.getKeyboardAdapter().enableControls();
        }

        @Override
        protected State execute() {
            System.err.println("ShowCredit.execute()");
            return new FinalScreen();
        }
    }

    private class Balance extends State {

        public Balance() {
            loadUpperScreen("balanceScreen.fxml");
            Service.getKeyboardAdapter().setActualController(new KeyboardAddMoney());
            Service.getKeyboardAdapter().enableControls();
        }

        @Override
        protected State execute() {
            System.err.println("Balance.execute()");
            return new FinalScreen();
        }
    }

    private class EjectCard extends State {

        public EjectCard() {
            loadUpperScreen("ejectCardScreen.fxml");
            Service.getKeyboardAdapter().setActualController(new KeyboardAddMoney());
            Service.getKeyboardAdapter().enableControls();
        }

        @Override
        protected State execute() {
            System.err.println("EjectCard.execute()");
            return new FinalScreen();
        }
    }

    private class Withdrawal extends State {

        public Withdrawal() {
            Service.getKeyboardAdapter().setActualController(new KeyboardWithdrawal());
            Service.getKeyboardAdapter().enableControls();
            loadUpperScreen("withdrawalScreen.fxml");
        }

        @Override
        protected State execute() {
            System.err.println("Withdrawal.execute()");
            return new FinalScreen();
        }
    }

    private class ShowHistory extends State {

        public ShowHistory() {
            loadUpperScreen("showHistoryScreen.fxml");
            Service.getKeyboardAdapter().setActualController(new KeyboardAddMoney());
            Service.getKeyboardAdapter().enableControls();
        }

        @Override
        protected State execute() {
            System.err.println("ShowHistory.execute()");
            return new FinalScreen();
        }
    }

    private class FinalScreen extends State {

        public FinalScreen() {
            loadUpperScreen("finalScreen.fxml");
            Service.getKeyboardAdapter().disableControls();
        }

        @Override
        protected State execute() {
            if (Service.getFSController().getContinue()) {
                Service.getKeyboardAdapter().disableControls();
                loadUpperScreen("mainMenuScreen.fxml");
                return new MainMenu();
            } else {
                System.err.println("return to cardSelectionScreen");
                Service.getKeyboardAdapter().setActualController(new KeyboardCardSelect());
                Service.getKeyboardAdapter().enableControls();
                loadUpperScreen("cardSelectionScreen.fxml");
//                Service.getCardSSController().addCard(new DebitCard());
//                Service.getCardSSController().addCard(new CreditCard());
                return new CardSelection();
            }
        }
    }
}
