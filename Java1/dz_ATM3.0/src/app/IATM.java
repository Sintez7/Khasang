package app;

import app.controller.exceptions.AtmIsBusyException;
import app.controller.exceptions.TimeoutException;
import app.model.bank.IBankRequest;
import app.model.bank.IBankResponse;
import app.model.bank.card.ICard;

public interface IATM {

    boolean insertCard(ICard card) throws AtmIsBusyException;

    boolean ejectCurrentCard();

    void queueOrder(IBankRequest request);

    void callbackResult(IBankResponse result);

    void requestTimedOut();
}
