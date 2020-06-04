package app;

import app.model.bank.IBankResponse;
import app.model.bank.card.ICard;

public interface IATM {

    void insertCard(ICard card);
    void ejectCurrentCard();
    IBankResponse queueOrder(IOrder order);
    void showBalance();
    void showHistory();
    void showCredit();

    void addMoney(double count);
}
