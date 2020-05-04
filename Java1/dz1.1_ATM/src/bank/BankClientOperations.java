package bank;

import bank.card.Card;
import bank.card.CardType;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class BankClientOperations {

    private BankClientOperationTypes type;

    private double operationSum;
    private Calendar operationDate;
    private Card operatedCard;
    private Calendar debtMustBeClosedBefore;
    private Calendar debtClosedDate;

    public BankClientOperations(BankClientOperationTypes type, double sum) {
        this.type = type;
        operationSum = sum;
        operationDate = Calendar.getInstance();
        debtMustBeClosedBefore = Calendar.getInstance();
        debtClosedDate = Calendar.getInstance();
    }

    public double getOperationSum() {
        return operationSum;
    }

    public Calendar getOperationDate() {
        return operationDate;
    }

    public Card getOperatedCard() {
        return operatedCard;
    }

    public void setOperatedCard(Card operatedCard) {
        this.operatedCard = operatedCard;
    }

    public Calendar getDebtMustBeClosedBefore() {
        return debtMustBeClosedBefore;
    }

    public void setDebtMustBeClosedBefore(Calendar debtMustBeClosedBefore) {
        this.debtMustBeClosedBefore = debtMustBeClosedBefore;
    }

    public Calendar getDebtClosedDate() {
        return debtClosedDate;
    }

    public void setDebtClosedDate(Calendar debtClosedDate) {
        this.debtClosedDate = debtClosedDate;
    }

    public void show() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        System.out.println("Operation date: " + dateFormat.format(operationDate.getTime()));
        System.out.println("Operation type: " + type);
        System.out.println("Operated card: " + operatedCard);
        switch (type) {
            case ADD:
                System.out.println("Debt closed date: " + dateFormat.format(debtClosedDate.getTime()));
                System.out.println();
                break;
            case WITHDRAWAL:
                System.out.println("Debt must be closed before: " + dateFormat.format(debtMustBeClosedBefore.getTime()));
                System.out.println();
                break;
        }
    }
}
