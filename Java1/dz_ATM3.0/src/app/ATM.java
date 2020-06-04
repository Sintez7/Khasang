package app;

import app.controller.exceptions.AtmIsBusyException;
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
        if (currentCard == null){
            return false;
        } else {
            currentCard = null;
            return true;
        }
    }

    @Override
    public IBankResponse queueOrder(IOrder order) {
        BankRequest request = new BankRequest(BankRequest.Type.PAYMENT);
        request.setSum(order.getTotalPrice());
        return delegateToMainBank(request);
    }

    @Override
    public void showBalance() {
        BankRequest request = new BankRequest(BankRequest.Type.BALANCE);
        IBankResponse response = delegateToMainBank(request);
//        response.show();
    }

    @Override
    public void showHistory() {
        BankRequest request = new BankRequest(BankRequest.Type.SHOW_HISTORY);
        delegateToMainBank(request);
    }

    @Override
    public void showCredit() {
        BankRequest request = new BankRequest(BankRequest.Type.SHOW_CREDIT);
        IBankResponse response = delegateToMainBank(request);
//        response.show();
    }

    @Override
    public void addMoney(double count) {
        BankRequest request = new BankRequest(BankRequest.Type.ADD_MONEY);
        request.setSum(count);
        IBankResponse response = delegateToMainBank(request);
//        response.show();
    }

    private IBankResponse delegateToMainBank(BankRequest request) {
        return mainBank.queue(currentCard.getCardInfo(), request);
    }
}
