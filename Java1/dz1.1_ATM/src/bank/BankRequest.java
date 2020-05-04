package bank;

public class BankRequest {

    public BankRequestTypes type;
    private double sum;

    public BankRequest(BankRequestTypes type) {
        this.type = type;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }
}
