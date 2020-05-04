package bank.card.cards;

import bank.IBank;
import bank.card.Card;
import bank.card.CardType;

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
