package app.controller;

import app.ATM;
import app.IATM;
import app.Order;
import app.User;
import app.controller.exceptions.*;
import app.model.DefaultModelData;
import app.model.MenuOption;
import app.model.Model;
import app.model.ModelData;
import app.model.bank.BankRequest;
import app.model.bank.IBankRequest;
import app.model.bank.IBankResponse;
import app.model.bank.card.ICard;
import app.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class BaseController implements Controller {

    protected Model model;
    protected View view;

    Timer curTimer;
    TimerTask curTimerTask;

    private ControllerStateMachine stateMachine = null;

    private List<ICard> cardsList = new ArrayList<>();

    @Override
    public Controller setModel(Model model) {
        this.model = model;
        return this;
    }

    @Override
    public Controller setView(View view) {
        this.view = view;
        return this;
    }

    @Override
    public void startUp(User user) {
        view.startUp(user);
    }

    @Override
    public boolean cardChosen(ICard card) throws AtmIsBusyException{
        return model.cardChosen(card);
    }

    @Override
    public boolean ejectCard() {
        return model.ejectCard();
    }

    @Override
    public void queueRequest(IBankRequest request) throws IllegalRequestTypeException, IllegalRequestSumException {
        MyQueue task = new MyQueue(request);
        task.start();
        System.err.println("BaseController " + Thread.currentThread());
        Timer timer = new Timer();
        curTimer = timer;
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                synchronized (request) {
                    System.err.println("Base Controller timer " + Thread.currentThread());
                    task.interrupt();
                    System.err.println("task.interrupt()");
                }
                callTimeout();
            }
        };
        curTimerTask = timerTask;
        timer.schedule(timerTask, 7000);
    }

    @Override
    public void callTimeout() {
        view.callTimeout();
    }

    @Override
    public void callbackResult(IBankResponse result) {
        if (curTimerTask.cancel()) {
            System.err.println("timer cancelled");
            view.callbackResult(result);
        } else {
            System.err.println("curTimerTask is false, not sending result to view");
        }
    }

    private class MyQueue extends Thread {

        IBankRequest request;

        public MyQueue(IBankRequest request) {
            this.request = request;
        }

        @Override
        public void run() {
            try {
                model.queueRequest(request);
            } catch (IllegalRequestSumException e) {
                e.printStackTrace();
            } catch (IllegalRequestTypeException e) {
                e.printStackTrace();
            }
        }
    }

//    public void setStateMachine(ControllerStateMachine stateMachine) {
//        this.stateMachine = stateMachine;
//    }
//
//    @Override
//    public boolean insertCard(ICard card) throws AtmIsBusyException {
//        return model.insertCard(card);
//    }
//
//    @Override
//    public boolean ejectCard() throws CardBusyException {
//        return model.ejectCard();
//    }
//
//    @Override
//    public synchronized ModelData queueRequest(Order order) throws IllegalRequestTypeException, IllegalRequestSumException {
//        switch (order.getType()) {
//            case PAYMENT:
//                return executeRequest(initPaymentRequest(order));
//            case BALANCE:
//                return executeRequest(initBalanceRequest());
//            case SHOW_CREDIT:
//                return executeRequest(initShowCreditRequest());
//            case SHOW_HISTORY:
//                return executeRequest(initShowHistoryRequest());
//            case ADD_MONEY:
//                return executeRequest(initAddMoneyRequest(order));
//            default:
//                throw new IllegalRequestTypeException();
//        }
//    }
//
//    private ModelData executeRequest(IBankRequest request) throws IllegalRequestTypeException, IllegalRequestSumException {
//        return new DefaultModelData(model.queueRequest(request), "");
//    }
//
//    private IBankRequest initPaymentRequest(Order order) {
//        IBankRequest temp = new BankRequest(IBankRequest.Type.PAYMENT);
//        temp.setSum(order.getSum());
//        return temp;
//    }
//
//    private IBankRequest initBalanceRequest() {
//        return new BankRequest(IBankRequest.Type.BALANCE);
//    }
//
//    private IBankRequest initShowCreditRequest() {
//        return new BankRequest(IBankRequest.Type.SHOW_CREDIT);
//    }
//
//    private IBankRequest initShowHistoryRequest() {
//        return new BankRequest(IBankRequest.Type.SHOW_HISTORY);
//    }
//
//    private IBankRequest initAddMoneyRequest(Order order) {
//        IBankRequest temp = new BankRequest(IBankRequest.Type.ADD_MONEY);
//        temp.setSum(order.getSum());
//        return temp;
//    }
//
//    @Override
//    public Object getControllerKey() {
//        return controllerMainKey;
//    }
//
//    @Override
//    public ArrayList<ModelData> getModelData() {
//        return model.getMessages();
//    }
//
//    @Override
//    public void setCards(ArrayList<ICard> cardsList) {
//        this.cardsList = cardsList;
//    }
//
//    @Override
//    public List<ICard> getCards() {
//        return cardsList;
//    }
//
//    @Override
//    public void addCard(ICard card) {
//        view.addCard(card);
//    }
//
//    @Override
//    public boolean processOkBtn() {
//        return model.processOkBtn();
//    }
//
//    @Override
//    public boolean processCancelBth() {
//        return false;
//    }
//
//    @Override
//    public List<MenuOption> getMenuOptions() {
//        return model.getMenuOptions();
//    }
//
//    @Override
//    public void confirmMenuOptionSelect(MenuOption option) {
//        model.confirmMenuOptionSelect(option);
//    }
//
//    @Override
//    public Controller setModel(Model model) {
//        this.model = model;
//        return this;
//    }
//
//    @Override
//    public Controller setView(View view) {
//        this.view = view;
//        return this;
//    }
//
//    @Override
//    public void startUp(User user) {
//
//    }

//    @Override
//    public boolean insertCard(ICard card) throws AtmIsBusyException {
//        return false;
//    }
//
//    @Override
//    public boolean ejectCard() throws CardBusyException {
//        return false;
//    }
//
//    @Override
//    public ModelData queueRequest(Order order) throws IllegalRequestTypeException, IllegalRequestSumException {
//        return null;
//    }
//
//    @Override
//    public Object getControllerKey() {
//        return null;
//    }
//
//    @Override
//    public ArrayList<ModelData> getModelData() {
//        return null;
//    }
//
//    @Override
//    public void setCards(ArrayList<ICard> cardsList) {
//
//    }
//
//    @Override
//    public List<ICard> getCards() {
//        return null;
//    }
//
//    @Override
//    public void addCard(ICard card) {
//
//    }
//
//    @Override
//    public boolean processOkBtn() {
//        return false;
//    }
//
//    @Override
//    public boolean processCancelBth() {
//        return false;
//    }
//
//    @Override
//    public List<MenuOption> getMenuOptions() {
//        return null;
//    }
//
//    @Override
//    public void confirmMenuOptionSelect(MenuOption option) {
//
//    }
//
//    @Override
//    public Controller setModel(Model model) {
//        this.model = model;
//        return this;
//    }
//
//    @Override
//    public Controller setView(View view) {
//        this.view = view;
//        return this;
//    }
//
//    @Override
//    public void startUp(User user) {
//
//    }
}
