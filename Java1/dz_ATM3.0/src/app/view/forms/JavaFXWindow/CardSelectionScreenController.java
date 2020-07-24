package app.view.forms.JavaFXWindow;

import java.net.URL;
import java.util.ResourceBundle;

import app.User;
import app.controller.Controller;
import app.model.bank.card.ICard;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class CardSelectionScreenController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<ICard> cardsContainer;

    private ObservableList<ICard> cardsList = FXCollections.observableArrayList();

    private volatile ICard selectedCard;
    private ATMMainWindow mainWindow;

    @FXML
    void initialize() {
//        Service.setCardSelectionScreenController(this);
        cardsContainer.setCellFactory(new Callback<ListView<ICard>, ListCell<ICard>>() {
            @Override
            public ListCell<ICard> call(ListView<ICard> cardListView) {
                return new CardCell();
            }
        });

        cardsContainer.setItems(cardsList);
    }

    synchronized void loadUserCards(User user) {
        cardsList.addAll(user.getUserCards());
    }

    public void setMainWindow(ATMMainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    synchronized public ICard getSelectedCard() {
        return selectedCard;
    }

    private class CardCell extends ListCell<ICard> {
        @Override
        synchronized protected void updateItem(ICard card, boolean b) {
            super.updateItem(card, b);

            if (card != null) {
                setText(card.getCardInfo().toString());
                selectedCard = card;
            }
        }
    }
}
