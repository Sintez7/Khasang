package app.model.bank.card;

/**
 * Serial
 * 100010 - 1000 версия атм, 10 версия класса
 */


import app.model.bank.ClientRequisites;

public abstract class Card implements ICard{

    private static final long serialVersionUID = 100010;
    private String bankName;
    private String cardNumber = "";
    private String cardCode = "";
    private String clientScriptedDNACode = "";
    private String quantumSign = "";

    private CardType cardType;

    public Card(String bankName) {
        this.bankName = bankName;
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
        return "Bank owner: " + bankName + "\n" +
                "Card number: " + cardNumber + "\n" +
                "Card code " + cardCode;
    }

    public String getCardBankName() {
        return bankName;
    }

    @Override
    public ClientRequisites getCardInfo() {
        ClientRequisites result = new ClientRequisites();
        result.setClientScriptedDNACode(getClientScriptedDNACode());
        result.setCardNumber(getCardNumber());
        result.setCardCode(getCardCode());
        result.setQuantumSign(getQuantumSign());
        result.setBankName(getCardBankName());
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
