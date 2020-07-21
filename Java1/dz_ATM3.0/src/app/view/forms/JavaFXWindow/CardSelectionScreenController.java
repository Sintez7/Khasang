package app.view.forms.JavaFXWindow;

import java.net.URL;
import java.util.ResourceBundle;

import app.model.bank.card.ICard;
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
    private ListView<ICard> cardsContainer;

    private ObservableList<ICard> cardsList = FXCollections.observableArrayList();

    private ICard selectedCard;

    @FXML
    void initialize() {
        Service.setCardSelectionScreenController(this);
        cardsContainer.setCellFactory(new Callback<ListView<ICard>, ListCell<ICard>>() {
            @Override
            public ListCell<ICard> call(ListView<ICard> cardListView) {
                return new CardCell();
            }
        });

        cardsContainer.setItems(cardsList);
    }

    synchronized public void addCard(ICard card) {
        cardsList.add(card);
    }

    synchronized public ICard getSelectedCard() {
        return selectedCard;
    }

    synchronized public void cardChosen() {

    }

    private class CardCell extends ListCell<ICard> {
        @Override
        protected void updateItem(ICard card, boolean b) {
            super.updateItem(card, b);

            if (card != null) {
                setPrefHeight(Region.USE_COMPUTED_SIZE);
                setText(card.getCardInfo().toString());
                selectedCard = card;
            }
        }
    }
}
