package app.controller;

import app.IOrder;
import app.model.bank.IBankResponse;
import app.model.bank.card.CardType;
import app.model.bank.card.ICard;

public interface Controller {

    ICard initNewCard(CardType type);

    void insertCard(ICard card);

    void ejectCard();

    IBankResponse queueRequest(IOrder order);
}
