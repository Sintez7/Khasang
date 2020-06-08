package app.controller;

import app.IATM;
import app.controller.exceptions.AtmIsBusyException;
import app.controller.exceptions.CardBusyException;
import app.controller.exceptions.IllegalRequestSumException;
import app.controller.exceptions.IllegalRequestTypeException;
import app.model.Model;
import app.model.bank.IBankRequest;
import app.model.bank.IBankResponse;
import app.model.bank.card.CardType;
import app.model.bank.card.ICard;

public class DefaultController implements Runnable, Controller{

    private final Object controllerMainKey = new Object();
    private final Model model;

    private State state = null;
    private IATM atm;
    private IBankRequest currentRequest;
    private RequestState currentRequestState;

    public DefaultController(Model model, IATM atm) {
        this.model = model;
        this.atm = atm;
        state = State.READY;
    }

    @Override
    public void run() {
        try {
            synchronized (controllerMainKey) {
                controllerMainKey.wait();
            }
        } catch (InterruptedException e) {}

    }

    @Override
    public boolean insertCard(ICard card) throws AtmIsBusyException {
        return model.insertCard(card);
    }

    @Override
    public boolean ejectCard() throws CardBusyException {
        return false;
    }

    @Override
    public RequestState isRequestReady() {
        return checkRequest();
    }

    private RequestState checkRequest() {
        if (currentRequest.getSum() == 0.0) {
            return RequestState.MISSING_SUM;
        }
        if (currentRequest.getType() == null) {
            return RequestState.MISSING_TYPE;
        }

        return RequestState.READY;
    }

    @Override
    public synchronized IBankResponse queueRequest(IBankRequest request) throws IllegalRequestTypeException, IllegalRequestSumException {
        if (currentRequestState == RequestState.READY) {
            return model.queueRequest(request);
        } else if (currentRequestState == RequestState.MISSING_SUM) {
            throw new IllegalRequestSumException();
        } else if (currentRequestState == RequestState.MISSING_TYPE) {
            throw new IllegalRequestTypeException();
        }
        return null;
    }

    @Override
    public Object getControllerKey() {
        return controllerMainKey;
    }

    enum RequestState {
        MISSING_TYPE,
        MISSING_SUM,
        READY
    }

    enum RequestType {
        PAYMENT,
        BALANCE,
        SHOW_HISTORY,
        SHOW_CREDIT,
        ADD_MONEY
    }

    private enum State {
        READY,
        WORKING_WITH_USER,
        WORKING_WITH_BANK
    }

    private class StateMachine {

    }
}
