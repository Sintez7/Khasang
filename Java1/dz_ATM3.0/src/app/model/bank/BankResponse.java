package app.model.bank;

import java.util.ArrayList;
import java.util.List;

public class BankResponse implements IBankResponse {

    private final Type type;
    private final String message;
    private List<String> messages = new ArrayList<>();

    public BankResponse(Type type) {
        this(type, "");
    }

    public BankResponse(Type type, String message) {
        this.type = type;
        this.message = message;
        messages.add("This response contains only list of messages");
    }

    public BankResponse(Type type, List<String> messages) {
        this.type = type;
        message = "This response contains only one message";
        this.messages = messages;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public List<String> getMessages() {
        return messages;
    }

    public enum Type {

        ACCEPTED,
        ACCEPTED_CREDIT,
        DENIED,
        BALANCE,
        HISTORY,
        SHOW_CREDIT,
        ACCEPTED_MONEY_ADDITION,
        TIMED_OUT
    }
}