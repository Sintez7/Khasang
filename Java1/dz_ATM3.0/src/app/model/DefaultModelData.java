package app.model;

import app.model.bank.IBankResponse;

public class DefaultModelData implements ModelData {

    private String message;
    private IBankResponse bankResponse;

    public DefaultModelData(IBankResponse bankResponse, String message) {
        this.bankResponse = bankResponse;
        this.message = message;
    }

    @Override
    public IBankResponse getBankResponse() {
        return bankResponse;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
