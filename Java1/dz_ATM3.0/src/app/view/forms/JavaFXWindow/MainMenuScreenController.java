package app.view.forms.JavaFXWindow;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MainMenuScreenController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    private Selected selectedOperation;

    @FXML
    void addMoney(ActionEvent event) {
        selectedOperation = Selected.ADD_MONEY;
        userChose();
    }

    @FXML
    void balance(ActionEvent event) {
        selectedOperation = Selected.BALANCE;
        userChose();
    }

    @FXML
    void ejectCard(ActionEvent event) {
        selectedOperation = Selected.EJECT_CARD;
        userChose();
    }

    @FXML
    void showCredit(ActionEvent event) {
        selectedOperation = Selected.SHOW_CREDIT;
        userChose();
    }

    @FXML
    void showHistory(ActionEvent event) {
        selectedOperation = Selected.SHOW_HISTORY;
        userChose();
    }

    @FXML
    void withdrawal(ActionEvent event) {
        selectedOperation = Selected.WITHDRAWAL;
        userChose();
    }

    @FXML
    void initialize() {
        ATMMainWindow.getInstance().registerMMSController(this);
    }

    private void userChose() {
        ATMMainWindow.getInstance().next();
    }

    synchronized public Selected getSelected() {
        return selectedOperation;
    }

    public enum Selected {
        ADD_MONEY,
        BALANCE,
        EJECT_CARD,
        SHOW_CREDIT,
        SHOW_HISTORY,
        WITHDRAWAL
    }
}
