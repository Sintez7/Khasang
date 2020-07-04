package app.view.forms;

import java.net.URL;
import java.util.ResourceBundle;

import app.App;
import app.model.bank.card.ICard;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Callback;

public class JFXWindowController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane centerScreenAnchor;

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

    @FXML
    private BorderPane cardsWindow;

    @FXML
    private ListView<ICard> cardsField;

    @FXML
    private Button insertCardBtn;

    @FXML
    private Button ejectCardBtn;

    @FXML
    private Label cardInfoLabel;

    private JFXWindow mainClass;
    private ObservableList<ICard> cardsList = FXCollections.observableArrayList();
    private ICard selectedCard;
    private ICard chosenCard;

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
        cardsField.setCellFactory(new Callback<ListView<ICard>, ListCell<ICard>>() {
            @Override
            public ListCell<ICard> call(ListView<ICard> iCardListView) {
                return new CardCell();
            }
        });
        cardsField.setItems(cardsList);

        synchronized (App.monitor) {
            App.monitor.notifyAll();
        }

        selectedCard = null;
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
//        mainClass.processStarBtn();
        mainClass.loadCenterComponent(centerScreenAnchor, "mainMenuComponent.fxml");
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

    @FXML
    void ejectCardBtn(ActionEvent event) {
        if (selectedCard != null) {
            String temp = mainClass.ejectCard();
            updateText(temp);
        }
    }

    @FXML
    void insertCardBtn(ActionEvent event) {
        if (selectedCard != null) {
            String temp = mainClass.insertCard(selectedCard);
            updateCardInfoLabel(temp);
        }
    }

    private void updateCardInfoLabel(String temp) {
        cardInfoLabel.setText(temp);
    }

    public void addCard(ICard card) {
        cardsList.add(card);
    }

    public void okBtnToSelectOption() {
        btnOk.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                mainClass.chooseOption();
            }
        });
    }

    private class CardCell extends ListCell<ICard> {

        @Override
        protected void updateItem(ICard iCard, boolean b) {
            super.updateItem(iCard, b);

            if (iCard != null) {
                setText(iCard.toString());
                selectedCard = iCard;
            } else {
                System.err.println("iCard is null");
            }
        }
    }
}
