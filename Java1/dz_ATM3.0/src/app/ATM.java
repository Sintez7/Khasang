package app;

import app.controller.exceptions.AtmIsBusyException;
import app.model.DefaultModel;
import app.model.Model;
import app.model.bank.*;
import app.model.bank.card.ICard;

public class ATM implements IATM {

    private static final long TIMEOUT_DELAY = 6000;

    private final IBank mainBank;
    private ICard currentCard;
    private final Model model;

    public Object monitor = new Object();

    public ATM(IBank bank, Model model) {
        mainBank = bank;
        this.model = model;
    }

    @Override
    public boolean insertCard(ICard card) throws AtmIsBusyException {
        if (currentCard == null) {
            currentCard = card;
            return true;
        } else {
            throw new AtmIsBusyException();
        }
    }

    @Override
    public boolean ejectCurrentCard() {
        System.err.println("entered ejectCurrentCard method");
        System.err.println(currentCard);
        if (currentCard == null) {
            return false;
        } else {
            currentCard = null;
            return true;
        }
    }

    @Override
    public void queueOrder(IBankRequest request) {

        delegateToMainBank(request);
    }

    @Override
    public void callbackResult(IBankResponse result) {
        model.callbackResults(result);
    }

    @Override
    public void requestTimedOut() {
        mainBank.requestTimedOut();
    }

    synchronized void delegateToMainBank(IBankRequest request) {
        mainBank.queue(currentCard.getCardInfo(), request, this);
    }

}
