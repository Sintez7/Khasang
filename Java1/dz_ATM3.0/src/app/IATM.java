package app;

import app.controller.exceptions.AtmIsBusyException;
import app.model.bank.IBankRequest;
import app.model.bank.IBankResponse;
import app.model.bank.card.ICard;

public interface IATM {

    boolean insertCard(ICard card) throws AtmIsBusyException;

    boolean ejectCurrentCard();

    IBankResponse queueOrder(IBankRequest request);

}
