package bank.banks;

import bank.*;
import bank.dataBases.UniversalBankDataBase;

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
