package bank.card.cards;

import bank.IBank;
import bank.card.Card;
import bank.card.CardType;

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
