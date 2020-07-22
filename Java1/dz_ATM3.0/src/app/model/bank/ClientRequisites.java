package app.model.bank;

public class ClientRequisites {

    private String cardNumber;
    private String cardCode;
    private String clientScriptedDNACode;
    private String quantumSign;
    private IBank bank;

    public String getClientScriptedDNACode() {
        return clientScriptedDNACode;
    }

    public void setClientScriptedDNACode(String clientScriptedDNACode) {
        this.clientScriptedDNACode = clientScriptedDNACode;
    }

    public String getQuantumSign() {
        return quantumSign;
    }

    public void setQuantumSign(String quantumSign) {
        this.quantumSign = quantumSign;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public IBank getBank() {
        return bank;
    }

    public void setBank(IBank bank) {
        this.bank = bank;
    }

    @Override
    public String toString() {
        return "Card requisites:\n" +
                "cardNumber: " + cardNumber + "\n" +
                "cardCode: " + cardCode + "\n" +
                "clientScriptedDNACode: " + clientScriptedDNACode + "\n" +
                "quantumSign: " + quantumSign + "\n" +
                "bankOwner: " + bank + "\n"
                ;
    }
}
