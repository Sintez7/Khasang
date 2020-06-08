package app.model;

import app.IATM;
import app.controller.exceptions.AtmIsBusyException;
import app.controller.exceptions.CardBusyException;
import app.controller.exceptions.IllegalRequestSumException;
import app.controller.exceptions.IllegalRequestTypeException;
import app.model.bank.IBankRequest;
import app.model.bank.IBankResponse;
import app.model.bank.card.CardType;
import app.model.bank.card.ICard;

public class DefaultModel implements Model {

    private IATM atm;

    public DefaultModel (IATM atm) {
        this.atm = atm;
    }

    @Override
    public void run() {

    }

    @Override
    public ICard initNewCard(CardType type) {
        return atm.;
    }

    @Override
    public boolean insertCard(ICard card) throws AtmIsBusyException {
        return false;
    }

    @Override
    public boolean ejectCard() throws CardBusyException {
        return false;
    }

    @Override
    public IBankResponse queueRequest(IBankRequest request) throws IllegalRequestTypeException, IllegalRequestSumException {
        return null;
    }
}
