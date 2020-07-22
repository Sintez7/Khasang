package app.view.forms.JavaFXWindow;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class KeyboardControllerAdapter {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane root;

    @FXML
    private Button btnOk;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnErase;

    @FXML
    private Button btnClear;

    @FXML
    private Button btn1;

    @FXML
    private Button btn2;

    @FXML
    private Button btn3;

    @FXML
    private Button btn4;

    @FXML
    private Button btn5;

    @FXML
    private Button btn6;

    @FXML
    private Button btn7;

    @FXML
    private Button btn8;

    @FXML
    private Button btn9;

    @FXML
    private Button btnStar;

    @FXML
    private Button btn0;

    @FXML
    private Button btnFence;

    private KeyboardController actualController;

    @FXML
    void initialize() {
        Service.setKeyboardAdapter(this);
    }

    public void setActualController(KeyboardController actualController) {
        this.actualController = actualController;
    }

    public void btn0Action() {
        actualController.btn0Action();
    }

    public void btn1Action() {
        actualController.btn1Action();
    }

    public void btn2Action() {
        actualController.btn2Action();
    }

    public void btn3Action() {
        actualController.btn3Action();
    }

    public void btn4Action() {
        actualController.btn4Action();
    }

    public void btn5Action() {
        actualController.btn5Action();
    }

    public void btn6Action() {
        actualController.btn6Action();
    }

    public void btn7Action() {
        actualController.btn7Action();
    }

    public void btn8Action() {
        actualController.btn8Action();
    }

    public void btn9Action() {
        actualController.btn9Action();
    }

    public void btnStarAction() {
        actualController.btnStarAction();
    }

    public void btnFenceAction() {
//        actualController.btnFenceAction();
        ATMMainWindow main = ATMMainWindow.getInstance();
        main.next();
    }

    public void btnOkAction() {
        actualController.btnOkAction();
    }

    public void btnCancelAction() {
        actualController.btnCancelAction();
    }

    public void btnEraseAction() {
        actualController.btnEraseAction();
    }

    public void btnClearAction() {
        actualController.btnClearAction();
    }

    public void disableControls() {
        root.setDisable(true);
    }

    public void enableControls() {
        root.setDisable(false);
    }
}
