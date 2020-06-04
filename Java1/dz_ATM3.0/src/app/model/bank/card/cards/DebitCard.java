package app.model.bank.card.cards;

import app.model.bank.IBank;
import app.model.bank.card.Card;
import app.model.bank.card.CardType;

public class DebitCard extends Card {
    public DebitCard(IBank bank) {
        super(bank);
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
