package app.model.bank;

public class BankResponse implements IBankResponse {

    private final Type type;
    private final String message;

    public BankResponse(Type type) {
        this(type, "");
    }

    public BankResponse(Type type, String message) {
        this.type = type;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Type getType() {
        return type;
    }

    public enum Type {

        ACCEPTED,
        ACCEPTED_CREDIT,
        DENIED,
        BALANCE,
        HISTORY,
        SHOW_CREDIT,
        ACCEPTED_MONEY_ADDITION
    }
}