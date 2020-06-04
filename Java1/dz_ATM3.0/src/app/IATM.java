package app;

import app.controller.exceptions.AtmIsBusyException;
import app.model.bank.IBankResponse;
import app.model.bank.card.ICard;

public interface IATM {

    boolean insertCard(ICard card) throws AtmIsBusyException;
    boolean ejectCurrentCard();
    IBankResponse queueOrder(IOrder order);
    void showBalance();
    void showHistory();
    void showCredit();

    void addMoney(double count);
}
