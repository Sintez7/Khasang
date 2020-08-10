package app.model.bank;

import app.model.bank.card.Card;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class BankClientOperations {

    private Type type;

    private double operationSum;
    private Calendar operationDate;
    private Card operatedCard;
    private Calendar debtMustBeClosedBefore;
    private Calendar debtClosedDate;

    public BankClientOperations(Type type, double sum) {
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

    public enum Type {
        ADD,
        WITHDRAWAL,
        DENIED_WITHDRAWAL
    }

    @Override
    public String toString() {
        String result = "";
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        result += "Operation date: " + dateFormat.format(operationDate.getTime()) + "\n";
        result += "Operation type: " + type + "\n";
        result += "Operated card: " + operatedCard + "\n";
        return result;
    }
}
