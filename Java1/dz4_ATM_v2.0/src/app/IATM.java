package app;

import bank.IBankResponse;
import bank.card.ICard;

public interface IATM {

    void insertCard(ICard card);
    void ejectCurrentCard();
    IBankResponse queueOrder(IOrder order);
    void showBalance();
    void showHistory();
    void showCredit();

    void addMoney(double count);
}
