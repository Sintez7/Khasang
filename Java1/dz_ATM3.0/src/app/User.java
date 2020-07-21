package app;

import app.model.bank.card.ICard;

import java.util.ArrayList;
import java.util.List;

public class User {

    private List<ICard> userCards = new ArrayList<>();

    public List<ICard> getUserCards() {
        return userCards;
    }

    public void addCard(ICard card) {
        userCards.add(card);
    }
}
