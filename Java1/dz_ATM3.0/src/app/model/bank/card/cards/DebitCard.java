package app.model.bank.card.cards;

import app.model.bank.IBank;
import app.model.bank.card.Card;
import app.model.bank.card.CardType;

public class DebitCard extends Card {
    private static final long serialVersionUID = 100010;

    public DebitCard(String bankName) {
        super(bankName);
    }

    @Override
    public void setCardType() {
        setCardType(CardType.DEBIT);
    }

    @Override
    public String toString() {
        return getInfoForPrint();
    }
}
