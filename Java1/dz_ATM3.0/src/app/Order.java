package app;

public abstract class Order {

    double sum;
    Type type;
    boolean withSum;

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

    public boolean isWithSum() {
        return withSum;
    }

    public enum Type {
        PAYMENT,
        BALANCE,
        SHOW_HISTORY,
        SHOW_CREDIT,
        ADD_MONEY
    }
}
