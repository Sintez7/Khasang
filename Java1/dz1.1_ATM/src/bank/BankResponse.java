package bank;

public class BankResponse implements IBankResponse {

    private BankResponses type;
    private String message;

    public BankResponse(BankResponses type) {
        this(type, "");
    }

    public BankResponse(BankResponses type, String message) {
        this.type = type;
        this.message = message;
    }

    public BankResponses getType() {
        return type;
    }

    public void setType(BankResponses type) {
        this.type = type;
    }

    @Override
    public void show() {
//        switch (type) {
//            case ACCEPTED:
//                printAcceptedResponse();
//                break;
//            case ACCEPTED_CREDIT:
//                printAcceptedCreditResponse();
//                break;
//            case DENIED:
//                printDeniedResponse();
//                break;
//            case BALANCE:
//                printBalance();
//                break;
//            case SHOW_CREDIT:
//                printCredit();
//            case ACCEPTED_MONEY_ADDITION:
//                printAddition();
//        }

        printMessage();
    }

    private void printAddition() {
        printMessage();
    }

    private void printCredit() {
        printMessage();
    }

    private void printAcceptedResponse() {
        System.out.println("Payment request accepted");
    }

    private void printAcceptedCreditResponse() {
        System.out.println("Payment request accepted as credit");
    }

    private void printDeniedResponse() {
        System.out.println("Payment request denied //play UT3narrator_DENIED.mp3\n" + message);
    }

    private void printBalance() {
        printMessage();
    }

    private void printMessage() {
        System.out.println(message);
    }
}
