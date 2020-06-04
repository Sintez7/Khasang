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

    private IATM atm;
    private IBankRequest currentRequest;

    public DefaultController(Model model, IATM atm) {
        this.model = model;
        this.atm = atm;
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
    public ICard initNewCard(CardType type) {
        return model.initNewCard(type);
    }

    @Override
    public boolean insertCard(ICard card) throws AtmIsBusyException {
        model.insertCard(card);
    }

    @Override
    public boolean ejectCard() throws CardBusyException {

    }

    @Override
    public IBankResponse queueRequest(IBankRequest request) throws IllegalRequestTypeException, IllegalRequestSumException {
        return model.queueRequest(request);
    }

    @Override
    public Object getControllerKey() {
        return controllerMainKey;
    }
}
