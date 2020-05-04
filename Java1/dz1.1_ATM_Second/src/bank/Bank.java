package bank;

import card.Card;

public abstract class Bank {

    public abstract void registerCard(Card card);
    public abstract Object transactionRequest();

}
