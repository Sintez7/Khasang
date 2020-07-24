package app.model.bank;

public interface IBankRequest {

    double getSum();

    void setSum(double sum);

    BankRequest.Type getType();

    enum Type {
        WITHDRAWAL,
        BALANCE,
        SHOW_HISTORY,
        SHOW_CREDIT,
        ADD_MONEY
    }
}
