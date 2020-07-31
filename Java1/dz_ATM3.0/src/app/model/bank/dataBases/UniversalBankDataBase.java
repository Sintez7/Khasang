package app.model.bank.dataBases;

import app.model.bank.BankClient;
import app.model.bank.ClientRequisites;

import java.util.ArrayList;
import java.util.List;

public class UniversalBankDataBase implements IBankDataBase {

    List<BankClient> db = new ArrayList<>();

    @Override
    public void add(BankClient client) {
        db.add(client);
    }

    @Override
    public BankClient getClient(ClientRequisites clientRequisites) {
        BankClient result = null;
        for (BankClient bankClient : db) {
            if (bankClient.getCustomerCardInfo().getClientScriptedDNACode().equals(clientRequisites.getClientScriptedDNACode())){
                result =  bankClient;
                break;
            }
        }
        return result;
    }
}
