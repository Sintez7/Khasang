package app;

public interface BankCardActions {
    boolean isPinCorrect(PinCode pinCode);

    boolean setPinCode(PinCode pinCode);
    PinCode getPinCode();

    String getOwner();
    boolean setOwner(String owner);

    String getCurrency();
    boolean setCurrency(String currency);

    Integer getBalance();
    boolean setBalance(Integer balance);
}
