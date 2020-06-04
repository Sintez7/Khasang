package app.model.bank.banks;

import app.model.bank.*;
import app.model.bank.dataBases.UniversalBankDataBase;

public class UniversalBank extends Bank {

    public UniversalBank() {
        setDataBase(new UniversalBankDataBase());
        SomeBankNetwork.registerBank(this);
    }

    @Override
    public String setName() {
        return "UniversalBank";
    }

    @Override
    public String toString() {
        return "UniversalBank";
    }
}
