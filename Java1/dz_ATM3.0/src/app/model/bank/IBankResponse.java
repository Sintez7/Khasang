package app.model.bank;

import java.util.List;

public interface IBankResponse {
    String getMessage();
    BankResponse.Type getType();
    List<String> getMessages();
}
