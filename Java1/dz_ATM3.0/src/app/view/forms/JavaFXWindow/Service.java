package app.view.forms.JavaFXWindow;

import javafx.application.Platform;

import java.util.Timer;
import java.util.TimerTask;

public class Service {

    public static ATMMainWindow atmMainWindow;
    public static CardSelectionScreenController cardSSController;
    public static KeyboardControllerAdapter keyboardAdapter;
    public static MainMenuScreenController mainMenuScreenController;
    public static AddMoneyScreenController addMoneyScreenController;
    public static FinalScreenController finalScreenController;
    public static WithdrawalScreenController withdrawalScreenController;

    synchronized public static void setCardSelectionScreenController(CardSelectionScreenController controller) {
        cardSSController = controller;
    }

    synchronized public static CardSelectionScreenController getCardSSController() {
        return cardSSController;
    }

    synchronized public static void callStarted() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.err.println("timer");
                Platform.runLater(() -> ATMMainWindow.getInstance().next());
            }
        }
        , 2000 + (Math.round(Math.random() * 1000)));
    }

    synchronized public static void callNext() {
        Platform.runLater(() -> ATMMainWindow.getInstance().next());
    }

    synchronized public static void setKeyboardAdapter(KeyboardControllerAdapter adapter) {
        keyboardAdapter = adapter;
    }

    synchronized public static KeyboardControllerAdapter getKeyboardAdapter() {
        return keyboardAdapter;
    }

    synchronized public static void setMainMenuScreenController(MainMenuScreenController controller) {
        mainMenuScreenController = controller;
    }

    synchronized public static MainMenuScreenController getMMSController() {
        return mainMenuScreenController;
    }

    synchronized public static void setAddMoneyScreenController(AddMoneyScreenController controller) {
        addMoneyScreenController = controller;
    }

    synchronized public static AddMoneyScreenController getAMSController() {
        return addMoneyScreenController;
    }

    synchronized public static FinalScreenController getFSController() {
        return finalScreenController;
    }

    synchronized public static void setFSController(FinalScreenController controller) {
        finalScreenController = controller;
    }

    synchronized public static void addWSController(WithdrawalScreenController controller) {
        withdrawalScreenController = controller;
    }

    synchronized public static WithdrawalScreenController getWSController() {
        return withdrawalScreenController;
    }
}
