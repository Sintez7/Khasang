package app.model;

import app.controller.exceptions.AtmIsBusyException;
import app.controller.exceptions.CardBusyException;
import app.controller.exceptions.IllegalRequestSumException;
import app.controller.exceptions.IllegalRequestTypeException;
import app.model.bank.IBankRequest;
import app.model.bank.IBankResponse;
import app.model.bank.card.CardType;
import app.model.bank.card.ICard;

public interface Model extends Runnable{

    ICard initNewCard(CardType type);

    boolean insertCard(ICard card) throws AtmIsBusyException;

    boolean ejectCard() throws CardBusyException;

    IBankResponse queueRequest(IBankRequest request) throws IllegalRequestTypeException, IllegalRequestSumException;
}
