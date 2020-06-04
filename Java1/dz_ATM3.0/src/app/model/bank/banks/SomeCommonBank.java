package app.model.bank.banks;

import app.model.bank.*;
import app.model.bank.dataBases.CommonBankDataBase;

public class SomeCommonBank extends Bank {

    public SomeCommonBank() {
        setDataBase(new CommonBankDataBase());
        SomeBankNetwork.registerBank(this);
    }

    @Override
    public String setName() {
        return "SomeCommonBank";
    }

    @Override
    public String toString() {
        return "SomeCommonBank";
    }
}
