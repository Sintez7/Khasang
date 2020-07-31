package app.model.bank.card.cards;

import app.model.bank.IBank;
import app.model.bank.card.Card;
import app.model.bank.card.CardType;

public class CreditCard extends Card {
    private static final long serialVersionUID = 100010;

    public CreditCard(String bankName) {
        super(bankName);
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
