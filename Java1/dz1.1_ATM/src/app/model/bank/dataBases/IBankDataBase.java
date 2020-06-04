package app.model.bank.dataBases;

import app.model.bank.BankClient;
import app.model.bank.ClientRequisites;

public interface IBankDataBase {

    void add(BankClient client);
    BankClient getClient(ClientRequisites clientRequisites);
}
