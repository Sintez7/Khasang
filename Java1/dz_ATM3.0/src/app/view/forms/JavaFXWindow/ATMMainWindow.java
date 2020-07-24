package app.view.forms.JavaFXWindow;

import app.User;
import app.controller.Controller;
import app.controller.exceptions.AtmIsBusyException;
import app.controller.exceptions.IllegalRequestSumException;
import app.controller.exceptions.IllegalRequestTypeException;
import app.model.bank.BankRequest;
import app.model.bank.IBankRequest;
import app.model.bank.IBankResponse;
import app.model.bank.card.ICard;
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

    private static ATMMainWindow atmMainWindow;
    private static final String CARD_SELECTION_SCREEN = "cardSelectionScreen.fxml";
    private static final String WITHDRAWAL_SCREEN = "withdrawalScreen.fxml";
    private static User user;
    private static Controller controller;

    private AnchorPane upperScreenAnchor;
    private AnchorPane lowerScreenAnchor;
    private StateMachine sm;
    private KeyboardControllerAdapter keyboard;
    private CardSelectionScreenController csController;
    private WithdrawalScreenController wsController;

//    public static void main(String[] args) {
//        launch(args);
//    }

    public ATMMainWindow() {
        atmMainWindow = this;
//        Service.setATMMainWindow(this);
    }

    synchronized public static ATMMainWindow getInstance() {
        if (atmMainWindow != null) {
            return atmMainWindow;
        } else {
            System.err.println("main is null");
            return null;
        }
    }

    public static void callLaunch(User u, Controller c) {
        user = u;
        controller = c;
        launch();
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
            System.err.println("tried to load: " + s);
        }
        System.err.println("loaded");
    }

    private void loadKeyboard() {
        FXMLLoader lowerScreenLoader = new FXMLLoader();
        lowerScreenLoader.setRoot(lowerScreenAnchor);
        lowerScreenLoader.setLocation(getClass().getResource("keyboard.fxml"));
        keyboard = new KeyboardControllerAdapter(new BlankKeyboardController());
        lowerScreenLoader.setController(keyboard);
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
        controller.setMainWindow(this);
        csController = controller;
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

    private void loadWithdrawalScreen() {
        System.err.println("upper screen loading");
        FXMLLoader loader = new FXMLLoader();
        if (loader.getRoot() != upperScreenAnchor) {
            loader.setRoot(upperScreenAnchor);
        }
        WithdrawalScreenController controller = new WithdrawalScreenController();
        loader.setLocation(getClass().getResource(WITHDRAWAL_SCREEN));
        controller.setMainWindow(this);
        wsController = controller;
        loader.setController(controller);
        upperScreenAnchor.getChildren().clear();
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("tried to load: " + WITHDRAWAL_SCREEN);
        }
        System.err.println("loaded");
    }

    void setUpperScreenAnchor(AnchorPane pane) {
        upperScreenAnchor = pane;
    }

    void setLowerScreenAnchor(AnchorPane pane) {
        lowerScreenAnchor = pane;
    }

    public void cardChosen() {
        try {
            if (controller.cardChosen(csController.getSelectedCard())) {
                next();
            }
        } catch (AtmIsBusyException e) {
            System.err.println("ATM currently busy with another card!_ATMMainWindow");
            e.printStackTrace();
        }
    }

    synchronized public void next() {
        sm.execute();
    }

    private class StateMachine {

        private State currentState;
        private State prevState;

        StateMachine() {
            currentState = new Starting();
            execute();
        }

        synchronized public void execute() {
            prevState = currentState;
            currentState = currentState.execute();
            if (currentState == null) {
                System.err.println("null state, returning to CardSelectionScreen_executeMethod");
                currentState = new CardSelection();
            }
        }

        synchronized public void eject() {
            prevState = currentState;
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
            if (controller.ejectCard()) {
                return new CardEjected();
            } else {
                System.err.println("ATM is already vacant");
                return this;
            }
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
            System.err.println("cannot eject card from starting screen!");
            return null;
        }
    }

    private class Welcome extends State {

        public Welcome() {
            keyboard.disableControls();
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
            keyboard.enableControls();
            loadCardSelectionScreen();
            keyboard.setActualController(new KeyboardCardSelect(ATMMainWindow.getInstance()));
        }

        @Override
        protected State execute() {
            return new MainMenu();
        }
    }

    private class MainMenu extends State {

        public MainMenu() {
            loadUpperScreen("mainMenuScreen.fxml");
            keyboard.disableControls();
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
            keyboard.setActualController(new KeyboardAddMoney());
            keyboard.enableControls();
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
            keyboard.setActualController(new KeyboardAddMoney());
            keyboard.enableControls();
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
            keyboard.setActualController(new KeyboardAddMoney());
            keyboard.enableControls();
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
            keyboard.setActualController(new KeyboardAddMoney());
            keyboard.enableControls();
        }

        @Override
        protected State execute() {
            System.err.println("EjectCard.execute()");
            return eject();
        }
    }

    private class Withdrawal extends State {

        public Withdrawal() {
            keyboard.setActualController(new KeyboardWithdrawal());
            keyboard.enableControls();
            loadWithdrawalScreen();
        }

        @Override
        protected State execute() {
            System.err.println("Withdrawal.execute()");
            IBankRequest request = new BankRequest(IBankRequest.Type.WITHDRAWAL);
            request.setSum(wsController.getSum());
            try {
                IBankResponse result = controller.queueRequest(request);
            } catch (IllegalRequestTypeException e) {
                e.printStackTrace();
            } catch (IllegalRequestSumException e) {
                System.err.println("IllegalRequestSumException");
                e.printStackTrace();
            }
            return new FinalScreen();
        }
    }

    private class ShowHistory extends State {

        public ShowHistory() {
            loadUpperScreen("showHistoryScreen.fxml");
            keyboard.setActualController(new KeyboardAddMoney());
            keyboard.enableControls();
        }

        @Override
        protected State execute() {
            System.err.println("ShowHistory.execute()");
            return new FinalScreen();
        }
    }

    private class CardEjected extends State {

        public CardEjected() {
            loadUpperScreen("cardEjectedScreen.fxml");
            keyboard.setActualController(new KeyboardAddMoney());
            keyboard.enableControls();
        }

        @Override
        protected State execute() {
            return new CardSelection();
        }
    }

    private class FinalScreen extends State {

        public FinalScreen() {
            loadUpperScreen("finalScreen.fxml");
            keyboard.disableControls();
        }

        @Override
        protected State execute() {
            if (Service.getFSController().getContinue()) {
                return new MainMenu();
            } else {
                System.err.println("return to cardSelectionScreen");
                return new CardSelection();
            }
        }
    }
}
