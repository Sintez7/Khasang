package app.model.bank;

import app.model.bank.card.Card;
import app.model.bank.card.CardType;
import app.model.bank.card.cards.CreditCard;
import app.model.bank.card.cards.DebitCard;

import java.io.Serializable;

public class CardFactory {

    public Card createCard(Bank bank, CardType cardType) {
        switch (cardType) {
            case DEBIT:
                return new DebitCard(bank.getBankName());
            case CREDIT:
                return new CreditCard(bank.getBankName());
            default:
                return null;
        }
    }
}
