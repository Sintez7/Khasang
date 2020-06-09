package app;

public abstract class Order {

    private double sum;
    private  Type type;

    private boolean withSum;

    public Order (Type type) {
        this.type = type;
        withSum = false;
    }

    public Order(Type type, double sum) {
        this.type = type;
        withSum = true;
    }

    public double getSum() {
        return sum;
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        PAYMENT,
        BALANCE,
        SHOW_HISTORY,
        SHOW_CREDIT,
        ADD_MONEY
    }
}
