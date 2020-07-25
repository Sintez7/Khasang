package app;

import app.controller.exceptions.AtmIsBusyException;
import app.controller.exceptions.TimeoutException;
import app.model.bank.*;
import app.model.bank.card.ICard;

public class ATM implements IATM {

    private IBank mainBank;
    private ICard currentCard;

    public ATM(IBank bank) {
        mainBank = bank;
    }

    @Override
    public boolean insertCard(ICard card) throws AtmIsBusyException{
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
        if (currentCard == null){
            return false;
        } else {
            currentCard = null;
            return true;
        }
    }

    @Override
    public IBankResponse queueOrder(IBankRequest request) throws TimeoutException {
        IBankResponse result;
//        result = () -> {
//            return new BankResponse(BankResponse.Type.ACCEPTED);
//        };
//        return delegateToMainBank(request);

    }

    synchronized private IBankResponse delegateToMainBank(IBankRequest request) {
        return mainBank.queue(currentCard.getCardInfo(), request);
    }

    private class MyQueue extends
}
