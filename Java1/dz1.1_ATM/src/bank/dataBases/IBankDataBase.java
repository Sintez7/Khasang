package bank.dataBases;

import bank.BankClient;
import bank.ClientRequisites;

public interface IBankDataBase {

    void add(BankClient client);
    BankClient getClient(ClientRequisites clientRequisites);
}
