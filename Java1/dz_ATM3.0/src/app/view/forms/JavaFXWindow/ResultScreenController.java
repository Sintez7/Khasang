package app.view.forms.JavaFXWindow;

import java.net.URL;
import java.util.ResourceBundle;

import app.model.bank.card.ICard;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import app.model.bank.BankResponse;
import app.model.bank.IBankResponse;
import javafx.util.Callback;

public class ResultScreenController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab singleStringTab;

    @FXML
    private Label label;

    @FXML
    private Tab listStringTab;

    @FXML
    private ListView<String> listView;

    private ObservableList<String> operationsList = FXCollections.observableArrayList();

    @FXML
    void initialize() {
//        tabPane.setTabMaxHeight(0);
//        tabPane.getStyleClass().add("tabCSS");

        listView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> listView) {
                return new OperationCell();
            }
        });

        listView.setItems(operationsList);
    }

    synchronized public void loadResult(IBankResponse result) {
        if (result.getType() == BankResponse.Type.HISTORY) {
            tabPane.getSelectionModel().select(listStringTab);
            loadHistory(result);
        } else {
            tabPane.getSelectionModel().select(singleStringTab);
            loadMessage(result);
        }
    }

    private void loadHistory(IBankResponse result) {
        System.err.println("load history");
//        label.setText(result.getMessage());
        operationsList.addAll(result.getMessages());
    }

    private void loadMessage(IBankResponse result) {
        System.err.println("load message");
        label.setText(result.getMessage());
    }

    private class OperationCell extends ListCell<String> {
        @Override
        synchronized protected void updateItem(String message, boolean b) {
            super.updateItem(message, b);

            if (message != null) {
                setText(message);
            }
        }
    }
}
