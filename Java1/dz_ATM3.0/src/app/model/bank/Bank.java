package app.model.bank;

import app.IATM;
import app.model.bank.card.Card;
import app.model.bank.card.CardType;
import app.model.bank.card.ICard;
import app.model.bank.dataBases.IBankDataBase;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public abstract class Bank implements IBank {

    private static final String NO_MONEY_WITHDRAWAL_MESSAGE = "На балансе недостаточно средств";
    private static final String ACCEPTED_MESSAGE = "Снятие наличных одобрено";
    private static final String ACCEPTED_AS_CREDIT_MESSAGE = "Снятие наличных одобрено как кредит";
    private static final String BALANCE_MESSAGE = "Текущий баланс: ";
    private static final String CREDIT_MESSAGE = "Текущий кредит: -";
    private static final String MONEY_ADDED_MESSAGE = "Баланс пополнен";

    IBankDataBase db;
    CardFactory cardFactory;
    private String bankName;
    private boolean requestTimedOut;
    private final Object requestMonitor = new Object();

    IBankResponse thisResult;
    IATM atm;

    public Bank() {
        initForTests();
    }

    private void initForTests() {
        cardFactory = new CardFactory();
        bankName = setName();
    }

    @Override
    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public void setDataBase(IBankDataBase db) {
        this.db = db;
    }

    public abstract String setName();

    @Override
    public IBankResponse queue(ClientRequisites costumer, IBankRequest request, IATM atm) {
        this.atm = atm;
        requestTimedOut = false;
        startTimer();

        IBankResponse result;

        synchronized (requestMonitor) {
            if (!requestTimedOut) {
                if (costumer.getBank().getBankName().equals(this.getBankName())) {
                    result = processRequest(costumer, request);
                } else {
                    result = delegateRequest(costumer, request, atm);
                }
            } else {
                result = new BankResponse(BankResponse.Type.TIMED_OUT);
            }
        }

        thisResult = result;
        return result;
    }

    protected void startTimer() {
        synchronized (requestMonitor) {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    sendResult();
                }
            }, Math.round(Math.random() * 10000));
        }

    }

    protected void sendResult() {
        atm.callbackResult(thisResult);
    }

    @Override
    public void requestTimedOut() {
        requestTimedOut = true;
    }

    public IBankResponse processRequest(ClientRequisites costumer, IBankRequest request) {
        IBankResponse result = null;
        switch (request.getType()) {
            case WITHDRAWAL:
                result = queueWithdrawalRequest(costumer, request);
                break;
            case BALANCE:
                result = queueGetCardBalance(costumer);
                break;
            case SHOW_HISTORY:
                result = showCardHistory(costumer);
                break;
            case SHOW_CREDIT:
                result = showCredit(costumer);
                break;
            case ADD_MONEY:
                result = addMoney(costumer, request);
                break;
        }
        return result;
    }

    public IBankResponse queueWithdrawalRequest(ClientRequisites costumer, IBankRequest request) {
        IBankResponse result = null;
        CardType cardType = getClient(costumer).getCard().getCardType();
        switch (cardType) {
            case DEBIT:
                result = processDebitPayment(costumer, request.getSum());
                break;
            case CREDIT:
                result = processCreditPayment(costumer, request.getSum());
                break;
            default:
        }
        return result;
    }

    private IBankResponse delegateRequest(ClientRequisites costumer, IBankRequest request, IATM atm) {
        return SomeBankNetwork.redirectRequest(costumer, request, atm);
    }

    @Override
    public IBankResponse requestBalance(ClientRequisites costumer) {
        return queueGetCardBalance(costumer);
    }

    @Override
    public ICard initNewCard(CardType type) {
        BankClient client = new BankClient();
        Card card = cardFactory.createCard(this, type);
        initCardStatsTest(card);
        client.setCard(card);
        client.setBalance(200);
        client.setCustomerCardInfo(card.getCardInfo());
        db.add(client);
        return card;
    }

    private void initCardStatsTest(Card card) {
        // Для тестов назначаем картам случайные значения, чтобы их различать между собой
        card.setClientScriptedDNACode("qwe" + getRandomInt());
        card.setCardCode("123abc" + getRandomInt());
        card.setCardNumber("321" + getRandomInt());
    }

    private int getRandomInt() {
        Random random = new Random();
        return random.nextInt(150000);
    }

    public IBankResponse queueGetCardBalance(ClientRequisites costumer) {
        IBankResponse result;
        if (costumer.getBank().getBankName().equals(this.getBankName())) {
            result = getCardBalance(getClient(costumer));
        } else {
            result = SomeBankNetwork.redirectGetBalance(costumer);
        }
        return result;
    }

    private IBankResponse getCardBalance(BankClient client) {
        return new BankResponse(BankResponse.Type.BALANCE, BALANCE_MESSAGE + client.getBalance());
    }

    private BankClient getClient(ClientRequisites requisites) {
        return db.getClient(requisites);
    }

    private BankResponse processDebitPayment(ClientRequisites costumer, double sum) {
        BankResponse response;
        BankClient client = getClient(costumer);
        if (client.getBalance() < sum) {
            client.queueOperation(BankClientOperations.Type.DENIED_WITHDRAWAL, sum);
            response = new BankResponse(BankResponse.Type.DENIED, NO_MONEY_WITHDRAWAL_MESSAGE);
        } else {
            client.queueOperation(BankClientOperations.Type.WITHDRAWAL, sum);
            response = new BankResponse(BankResponse.Type.ACCEPTED, ACCEPTED_MESSAGE);
        }
        return response;
    }

    private BankResponse processCreditPayment(ClientRequisites costumer, double sum) {
        BankResponse response;
        BankClient client = getClient(costumer);
        if (client.getBalance() < sum) {
            if (creditOperationPossible(client, sum)) {
                client.queueOperation(BankClientOperations.Type.DENIED_WITHDRAWAL, sum);
                response = new BankResponse(BankResponse.Type.DENIED, NO_MONEY_WITHDRAWAL_MESSAGE);
            } else {
                client.queueOperation(BankClientOperations.Type.WITHDRAWAL, sum);
                response = new BankResponse(BankResponse.Type.ACCEPTED_CREDIT, ACCEPTED_AS_CREDIT_MESSAGE);
            }
        } else {
            client.queueOperation(BankClientOperations.Type.WITHDRAWAL, sum);
            response = new BankResponse(BankResponse.Type.ACCEPTED, ACCEPTED_MESSAGE);
        }
        return response;
    }

    private boolean creditOperationPossible(BankClient client, double sum) {
        return client.getCurrentCredit() - client.getBalance() + sum > client.getCreditLimit();
    }

    @Override
    public IBankResponse showCredit(ClientRequisites costumer) {
        BankClient client = getClient(costumer);
        return new BankResponse(BankResponse.Type.SHOW_CREDIT, CREDIT_MESSAGE + client.getCurrentCredit());
    }

    @Override
    public IBankResponse showCardHistory(ClientRequisites costumer) {
        //        getClient(costumer).showHistory();
        return new BankResponse(BankResponse.Type.HISTORY, getClient(costumer).getHistory());
    }

    private IBankResponse addMoney(ClientRequisites costumer, IBankRequest request) {
        BankClient client = getClient(costumer);
        client.queueOperation(BankClientOperations.Type.ADD, request.getSum());
        return new BankResponse(BankResponse.Type.ACCEPTED_MONEY_ADDITION, MONEY_ADDED_MESSAGE);
    }
}
