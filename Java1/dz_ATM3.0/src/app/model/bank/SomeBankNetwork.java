package app.model.bank;

import app.IATM;

import java.util.ArrayList;
import java.util.List;

public class SomeBankNetwork {

    private static List<IBank> bankList = new ArrayList<>();

    public static void registerBank (IBank bank) {
        bankList.add(bank);
    }

    public static IBankResponse redirectRequest(ClientRequisites client, IBankRequest request, IATM atm) {
        IBankResponse result = null;
        for (IBank bank : bankList) {
            if (client.getBank().getBankName().equals(bank.getBankName())) {
                result = bank.queue(client, request, atm);
            }
        }
        return result;
    }

    public static IBankResponse redirectGetBalance(ClientRequisites client) {
        IBankResponse result = null;
        for (IBank bank : bankList) {
            if (client.getBank().getBankName().equals(bank.getBankName())) {
                result = bank.requestBalance(client);
            }
        }
        return result;
    }
}
