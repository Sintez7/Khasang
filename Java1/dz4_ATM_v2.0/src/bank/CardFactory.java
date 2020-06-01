package bank;

import bank.card.Card;
import bank.card.CardType;
import bank.card.cards.CreditCard;
import bank.card.cards.DebitCard;

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
