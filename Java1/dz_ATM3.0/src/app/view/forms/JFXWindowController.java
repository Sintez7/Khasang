package app.view.forms;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class JFXWindowController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label labelText;

    @FXML
    private Button btn1;

    @FXML
    private Button btn2;

    @FXML
    private Button btn3;

    @FXML
    private Button btnOk;

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

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnErase;

    @FXML
    private Button btnClear;

    private JFXWindow mainClass;

    @FXML
    void initialize() {
//        assert labelText != null : "fx:id=\"labelText\" was not injected: check your FXML file 'sample.fxml'.";
//        assert btn1 != null : "fx:id=\"btn1\" was not injected: check your FXML file 'sample.fxml'.";
//        assert btn2 != null : "fx:id=\"btn2\" was not injected: check your FXML file 'sample.fxml'.";
//        assert btn3 != null : "fx:id=\"btn3\" was not injected: check your FXML file 'sample.fxml'.";
//        assert btnOk != null : "fx:id=\"btnOk\" was not injected: check your FXML file 'sample.fxml'.";
//        assert btn4 != null : "fx:id=\"btn4\" was not injected: check your FXML file 'sample.fxml'.";
//        assert btn5 != null : "fx:id=\"btn5\" was not injected: check your FXML file 'sample.fxml'.";
//        assert btn6 != null : "fx:id=\"btn6\" was not injected: check your FXML file 'sample.fxml'.";
//        assert btn7 != null : "fx:id=\"btn7\" was not injected: check your FXML file 'sample.fxml'.";
//        assert btn8 != null : "fx:id=\"btn8\" was not injected: check your FXML file 'sample.fxml'.";
//        assert btn9 != null : "fx:id=\"btn9\" was not injected: check your FXML file 'sample.fxml'.";
//        assert btnStar != null : "fx:id=\"btnStar\" was not injected: check your FXML file 'sample.fxml'.";
//        assert btn0 != null : "fx:id=\"btn0\" was not injected: check your FXML file 'sample.fxml'.";
//        assert btnFence != null : "fx:id=\"btnFence\" was not injected: check your FXML file 'sample.fxml'.";
//        assert btnCancel != null : "fx:id=\"btnCancel\" was not injected: check your FXML file 'sample.fxml'.";
//        assert btnErase != null : "fx:id=\"btnErase\" was not injected: check your FXML file 'sample.fxml'.";
        JFXWindow.setWindowController(this);
    }

    public void setMainClass (JFXWindow mainWindow) {
        mainClass = mainWindow;
    }

    public void updateText(String text) {
        labelText.setText(text);
    }

    @FXML
    void processOkBtn(ActionEvent event) {
        mainClass.processOkBtn();
    }

    @FXML
    void processCancelBtn(ActionEvent event) {
        mainClass.processCancelBtn();
    }

    @FXML
    void processEraseBtn(ActionEvent event) {
        String temp = labelText.getText();
        if (temp.length() > 0) updateText(temp.substring(0, temp.length() - 1));
    }

    @FXML
    void processClearBtn(ActionEvent event) {
        updateText("");
    }

    @FXML
    void processStarBtn(ActionEvent event) {
        mainClass.processStarBtn();
    }

    @FXML
    void processFenceBtn(ActionEvent event) {
        mainClass.processFenceBtn();
    }

    @FXML
    void processNumberBtn(ActionEvent event) {
        String temp = labelText.getText();
        temp += ((Button)event.getSource()).getAccessibleText();
        updateText(temp);
    }
}
