package app.model;

import app.IATM;
import app.controller.exceptions.*;
import app.model.bank.IBankRequest;
import app.model.bank.IBankResponse;
import app.model.bank.card.ICard;

public class DefaultModel implements Model {

    private final Object modelMainKey = new Object();
    private IATM atm;

    public DefaultModel (IATM atm) {
        this.atm = atm;
    }

    @Override
    public void run() {
        try {
            synchronized (modelMainKey) {
                modelMainKey.wait();
            }
        } catch (InterruptedException e) {}
    }

    @Override
    public boolean insertCard(ICard card) throws AtmIsBusyException {
        return atm.insertCard(card);
    }

    @Override
    public boolean ejectCard() throws CardBusyException {
        return atm.ejectCurrentCard();
    }

    @Override
    public IBankResponse queueRequest(IBankRequest request) throws IllegalRequestTypeException, IllegalRequestSumException {
        return atm.queueOrder(request);
    }
}
