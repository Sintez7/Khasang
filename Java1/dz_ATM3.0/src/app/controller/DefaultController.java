package app.controller;

import app.IATM;
import app.Order;
import app.controller.exceptions.AtmIsBusyException;
import app.controller.exceptions.CardBusyException;
import app.controller.exceptions.IllegalRequestSumException;
import app.controller.exceptions.IllegalRequestTypeException;
import app.model.DefaultModelData;
import app.model.Model;
import app.model.ModelData;
import app.model.bank.BankRequest;
import app.model.bank.IBankRequest;
import app.model.bank.card.ICard;
import app.view.View;

import java.util.ArrayList;
import java.util.List;

public class DefaultController implements Runnable, Controller {

    private final Object controllerMainKey = new Object();
    private final Model model;
    private final View view;

    private ControllerStateMachine stateMachine = null;

    private State state = null;
    private IATM atm;
    private IBankRequest currentRequest;
    private RequestState currentRequestState;

    private List<ICard> cardsList = new ArrayList<>();

    public DefaultController(Model model, IATM atm, View view) {
        this.model = model;
        this.atm = atm;
        this.view = view;
        stateMachine = new StateMachine();
        state = State.FIRST_LAUNCH;
    }

    public void setStateMachine(ControllerStateMachine stateMachine) {
        this.stateMachine = stateMachine;
    }



    @Override
    public void run() {
//        try {
//            synchronized (controllerMainKey) {
//                controllerMainKey.wait();
//            }
//        } catch (InterruptedException e) {}

//        while (true) {
//            executeStateMethod();
//        }

    }

    private void executeStateMethod() {
        stateMachine.execute(state);
    }

    @Override
    public boolean insertCard(ICard card) throws AtmIsBusyException {
        return model.insertCard(card);
    }

    @Override
    public boolean ejectCard() throws CardBusyException {
        return false;
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
    public synchronized ModelData queueRequest(Order order) throws IllegalRequestTypeException, IllegalRequestSumException {
        switch (order.getType()) {
            case PAYMENT:
                return executeRequest(initPaymentRequest(order));
            case BALANCE:
                return executeRequest(initBalanceRequest());
            case SHOW_CREDIT:
                return executeRequest(initShowCreditRequest());
            case SHOW_HISTORY:
                return executeRequest(initShowHistoryRequest());
            case ADD_MONEY:
                return executeRequest(initAddMoneyRequest(order));
            default:
                throw new IllegalRequestTypeException();
        }
    }

    private ModelData executeRequest(IBankRequest request) throws IllegalRequestTypeException, IllegalRequestSumException {
        return new DefaultModelData(model.queueRequest(request), "");
    }

    private IBankRequest initPaymentRequest(Order order) {
        IBankRequest temp = new BankRequest(IBankRequest.Type.PAYMENT);
        temp.setSum(order.getSum());
        return temp;
    }

    private IBankRequest initBalanceRequest() {
        return new BankRequest(IBankRequest.Type.BALANCE);
    }

    private IBankRequest initShowCreditRequest() {
        return new BankRequest(IBankRequest.Type.SHOW_CREDIT);
    }

    private IBankRequest initShowHistoryRequest() {
        return new BankRequest(IBankRequest.Type.SHOW_HISTORY);
    }

    private IBankRequest initAddMoneyRequest(Order order) {
        IBankRequest temp = new BankRequest(IBankRequest.Type.ADD_MONEY);
        temp.setSum(order.getSum());
        return temp;
    }

    @Override
    public Object getControllerKey() {
        return controllerMainKey;
    }

    @Override
    public ArrayList<ModelData> getModelData() {
        return model.getMessages();
    }

    @Override
    public void setCards(ArrayList<ICard> cardsList) {
        this.cardsList = cardsList;
    }

    @Override
    public List<ICard> getCards() {
        return cardsList;
    }

    @Override
    public void addCard(ICard card) {
        view.addCard(card);
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

    public enum State {
        FIRST_LAUNCH,
        READY,
        WORKING_WITH_USER,
        WORKING_WITH_BANK
    }

    private class StateMachine implements ControllerStateMachine{

        @Override
        public void execute(State state) {
            switch (state) {
                case FIRST_LAUNCH:
                    prepare();
                    break;
                case READY:
                    break;
                case WORKING_WITH_BANK:
                    break;
                case WORKING_WITH_USER:
                    break;
                default:
                    break;
            }
        }

        private void prepare() {
            state = State.READY;
            view.update(null);
        }
    }
}
