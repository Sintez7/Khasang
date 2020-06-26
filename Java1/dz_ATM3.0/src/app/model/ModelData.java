package app.model;

import app.model.bank.IBankResponse;

public interface ModelData {
    IBankResponse getBankResponse();
    String getMessage();
}
