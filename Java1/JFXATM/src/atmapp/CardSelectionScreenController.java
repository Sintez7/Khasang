package atmapp;

import java.net.URL;
import java.util.ResourceBundle;

import atmapp.model.card.Card;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.util.Callback;

public class CardSelectionScreenController {


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<Card> cardsContainer;

    private ObservableList<Card> cardsList = FXCollections.observableArrayList();

    private Card selectedCard;

    @FXML
    void initialize() {
        Service.setCardSelectionScreenController(this);
        cardsContainer.setCellFactory(new Callback<ListView<Card>, ListCell<Card>>() {
            @Override
            public ListCell<Card> call(ListView<Card> cardListView) {
                return new CardCell();
            }
        });

        cardsContainer.setItems(cardsList);
    }

    synchronized public void addCard(Card card) {
        cardsList.add(card);
    }

    synchronized public Card getSelectedCard() {
        return selectedCard;
    }

    synchronized public void cardChosen() {

    }

    private class CardCell extends ListCell<Card> {
        @Override
        protected void updateItem(Card card, boolean b) {
            super.updateItem(card, b);

            if (card != null) {
                setPrefHeight(Region.USE_COMPUTED_SIZE);
                setText(card.getCardInfo());
                selectedCard = card;
            }
        }
    }
}
