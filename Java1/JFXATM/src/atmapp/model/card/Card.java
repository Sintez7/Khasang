package atmapp.model.card;

import java.util.ArrayList;
import java.util.List;

public abstract class Card {

    private String bankOwner;
    private String number;

    public Card () {
        this(null);
    }

    public Card (String bankOwner) {
        this(bankOwner, null);
    }

    public Card (String bankOwner, String number) {
        if (bankOwner == null) {
            this.bankOwner = "Undefined Bank";
        }

        if (number == null) {
            this.number = Double.toString(Math.floor(Math.random() * 1000));
        }
    }

    public String getBankOwner() {
        return bankOwner;
    }

    protected void setBankOwner(String bankOwner) {
        this.bankOwner = bankOwner;
    }

    public String getNumber() {
        return number;
    }

    protected void setNumber(String number) {
        this.number = number;
    }

    public String getCardInfo() {
        return bankOwner + "\n" + number;
    }
}
