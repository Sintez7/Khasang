package app.model.bank;

import app.model.bank.card.Card;
import app.model.bank.card.CardType;
import app.model.bank.card.cards.CreditCard;
import app.model.bank.card.cards.DebitCard;

public class CardFactory {

    public Card createCard(Bank bank, CardType cardType) {
        switch (cardType) {
            case DEBIT:
                return new DebitCard(bank);
            case CREDIT:
                return new CreditCard(bank);
            default:
                return null;
        }
    }
}
