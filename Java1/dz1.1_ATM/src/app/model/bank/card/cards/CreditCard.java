package app.model.bank.card.cards;

import app.model.bank.IBank;
import app.model.bank.card.Card;
import app.model.bank.card.CardType;

public class CreditCard extends Card {
    public CreditCard(IBank bank) {
        super(bank);
    }

    @Override
    public void setCardType() {
        setCardType(CardType.CREDIT);
    }

    @Override
    public String toString() {
        return getInfoForPrint();
    }
}
