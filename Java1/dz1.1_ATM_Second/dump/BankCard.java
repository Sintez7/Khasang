package app;

public class BankCard implements BankCardActions {
    private PinCode pinCode;
    private String owner;
    private Integer balance;
    private String currency;

    @Override
    public boolean isPinCorrect(PinCode pinCode) {
        return this.pinCode.isMatch(pinCode);
    }

    @Override
    public boolean setPinCode(PinCode pinCode) {
        this.pinCode = pinCode;
        return true;
    }

    @Override
    public PinCode getPinCode() {
        return pinCode;
    }

    @Override
    public String getOwner() {
        return this.owner;
    }

    @Override
    public boolean setOwner(String owner) {
        this.owner = owner;
        return true;
    }

    @Override
    public String getCurrency() {
        return this.currency;
    }

    @Override
    public boolean setCurrency(String currency) {
        this.currency = currency;
        return true;
    }

    @Override
    public Integer getBalance() {
        return this.balance;
    }

    @Override
    public boolean setBalance(Integer balance) {
        this.balance = balance;
        return true;
    }

    @Override
    public String toString() {
        return "BankCard{" +
                "pinCode=" + pinCode +
                ", owner='" + owner + '\'' +
                ", balance=" + balance +
                ", currency='" + currency + '\'' +
                '}';
    }
}
