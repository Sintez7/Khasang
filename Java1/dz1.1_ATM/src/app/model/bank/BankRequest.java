package app.model.bank;

public class BankRequest implements IBankRequest {

    private final Type type;
    private double sum;

    public BankRequest(Type type) {
        this.type = type;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public Type getType() {
        return type;
    }
}