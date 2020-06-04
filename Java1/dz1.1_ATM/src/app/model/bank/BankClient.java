package app.model.bank;

import app.model.bank.card.Card;

import java.util.ArrayList;
import java.util.List;

public class BankClient {

    private ClientRequisites customerCardInfo;
    private Card card;
    private List<BankClientOperations> operationsHistory = new ArrayList<>();
    private BankClientBalance balance = new BankClientBalance();


    public ClientRequisites getCustomerCardInfo() {
        return customerCardInfo;
    }

    public void setCustomerCardInfo(ClientRequisites customerCardInfo) {
        this.customerCardInfo = customerCardInfo;
    }

    public double getBalance() {
        return balance.getAvailableSum();
    }

    public void setBalance(double balance) {
        this.balance.setAvailableSum(balance);
    }

    public double getCurrentCredit() {
        return balance.getCreditSum();
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public double getCreditLimit() {
        return balance.getCreditMaximum();
    }

    public void queueOperation(BankClientOperations.Type type, double sum) {
        BankClientOperations operation;
        switch (type) {
            case WITHDRAWAL:
                operation = new BankClientOperations(BankClientOperations.Type.WITHDRAWAL, sum);
                operation.setOperatedCard(card);
                operationsHistory.add(operation);
                balance.withdrawalOperation(sum);
                break;
            case ADD:
                operation = new BankClientOperations(BankClientOperations.Type.ADD, sum);
                operation.setOperatedCard(card);
                operationsHistory.add(operation);
                balance.addMoney(sum);
                break;
        }
    }

    public void showHistory() {
        for (BankClientOperations op : operationsHistory) {
            op.show();
        }
    }
}
