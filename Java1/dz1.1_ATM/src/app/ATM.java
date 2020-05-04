package app;

import bank.*;
import bank.card.ICard;

public class ATM implements IATM {

    private IBank mainBank;
    private ICard currentCard;

    public ATM(IBank bank) {
        mainBank = bank;
    }

    @Override
    public void insertCard(ICard card) {
        if (currentCard == null) {
            currentCard = card;
            System.out.println("Карта вставлена");
            System.out.println();

        } else {
            System.out.println("Карта уже вставлена");
        }
    }

    @Override
    public void ejectCurrentCard() {
        if (currentCard == null){
            System.out.println("Карты нет в банкомате");
        } else {
            currentCard = null;
            System.out.println("Карта извлечена");
        }
    }

    @Override
    public IBankResponse queueOrder(IOrder order) {
        BankRequest request = new BankRequest(BankRequestTypes.PAYMENT);
        request.setSum(order.getTotalPrice());
        return delegateToMainBank(request);
    }

    @Override
    public void showBalance() {
        BankRequest request = new BankRequest(BankRequestTypes.BALANCE);
        IBankResponse response = delegateToMainBank(request);
        response.show();
    }

    @Override
    public void showHistory() {
        BankRequest request = new BankRequest(BankRequestTypes.SHOW_HISTORY);
        delegateToMainBank(request);
    }

    @Override
    public void showCredit() {
        BankRequest request = new BankRequest(BankRequestTypes.SHOW_CREDIT);
        IBankResponse response = delegateToMainBank(request);
        response.show();
    }

    @Override
    public void addMoney(double count) {
        BankRequest request = new BankRequest(BankRequestTypes.ADD_MONEY);
        request.setSum(count);
        IBankResponse response = delegateToMainBank(request);
        response.show();
    }

    private IBankResponse delegateToMainBank(BankRequest request) {
        return mainBank.queue(currentCard.getCardInfo(), request);
    }
}
