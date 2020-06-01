package bank.card;

import bank.ClientRequisites;
import bank.IBank;

public abstract class Card implements ICard {

    private IBank bank;
    private String cardNumber = "";
    private String cardCode = "";
    private String clientScriptedDNACode = "";

    private String quantumSign = "";

    private CardType cardType;

    public Card(IBank bank) {
        this.bank = bank;
        setCardType();
    }

    public abstract void setCardType();

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        if (this.cardNumber.equals("")) {
            this.cardNumber = cardNumber;
        } else {

        }
    }

    public void forcedCardNumberRewrite(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        if (this.cardCode.equals("")) {
            this.cardCode = cardCode;
        } else {

        }
    }

    public String getQuantumSign() {
        return quantumSign;
    }

    public void setQuantumSign(String quantumSign) {
        this.quantumSign = quantumSign;
    }

    public String getClientScriptedDNACode() {
        return clientScriptedDNACode;
    }

    public void setClientScriptedDNACode(String clientScriptedDNACode) {
        this.clientScriptedDNACode = clientScriptedDNACode;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public String getInfoForPrint() {
        return "Bank owner: " + bank + "\n" +
                "Card number: " + cardNumber + "\n" +
                "Card code " + cardCode;
    }

    public IBank getCardBank() {
        return bank;
    }

    @Override
    public ClientRequisites getCardInfo() {
        ClientRequisites result = new ClientRequisites();
        result.setClientScriptedDNACode(getClientScriptedDNACode());
        result.setCardNumber(getCardNumber());
        result.setCardCode(getCardCode());
        result.setQuantumSign(getQuantumSign());
        result.setBank(getCardBank());
        return result;
    }

//    @Override
//    public ClientRequisites getCardInfo() {
//        ClientRequisites myRequisites = new ClientRequisites();
//        myRequisites.setCardNumber(cardNumber);
//        myRequisites.setCardCode(cardCode);
//        return myRequisites;
//    }
}
