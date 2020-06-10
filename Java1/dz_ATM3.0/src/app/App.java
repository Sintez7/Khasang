package app;

import app.controller.Controller;
import app.controller.DefaultController;
import app.controller.exceptions.AtmIsBusyException;
import app.controller.exceptions.IllegalRequestSumException;
import app.controller.exceptions.IllegalRequestTypeException;
import app.model.DefaultModel;
import app.model.Model;
import app.model.bank.BankResponse;
import app.model.bank.IBank;
import app.model.bank.IBankRequest;
import app.model.bank.IBankResponse;
import app.model.bank.banks.SomeCommonBank;
import app.model.bank.banks.UniversalBank;
import app.model.bank.card.CardType;
import app.model.bank.card.ICard;
import app.view.DefaultView;
import app.view.View;

public class App {

    private Model model;
    private View view;
    private Controller controller;

    private IBank uBank;
    private IBank commonBank;

    private ICard uCard;
    private ICard uCreditCard;
    private ICard commonCard;
    private ICard commonCreditCard;

    private IATM atm;

    public void start() {
        initModules();
//        mainCycle();
        new Thread(controller).start();
        new Thread(model).start();
        new Thread(view).start();

        runRandomTests();
    }

    private void initModules() {
        uBank = new UniversalBank();
        commonBank = new SomeCommonBank();

        uCard = uBank.initNewCard(CardType.DEBIT);
        uCreditCard = uBank.initNewCard(CardType.CREDIT);

        commonCard = commonBank.initNewCard(CardType.DEBIT);
        commonCreditCard = commonBank.initNewCard(CardType.CREDIT);

        atm = new ATM(uBank);

        model = new DefaultModel(atm);
        controller = new DefaultController(model, atm);
        view = new DefaultView();
    }

    private void runRandomTests() {
        // тут тесты
        /*
        IBankResponse response = null;
        ICard card = uBank.initNewCard(CardType.DEBIT);
        try {
            controller.insertCard(card);
        } catch (AtmIsBusyException e) {
            e.printStackTrace();
        }

        try {
            response = controller.queueRequest(new Order(Order.Type.PAYMENT, 152) {
                @Override
                public double getSum() {
                    return sum;
                }

                @Override
                public Type getType() {
                    return type;
                }

                @Override
                public boolean isWithSum() {
                    return withSum;
                }
            });
        } catch (IllegalRequestTypeException e) {
            e.printStackTrace();
        } catch (IllegalRequestSumException e) {
            e.printStackTrace();
        }

        if (response != null) {
            System.out.println(response.getMessage());
        }

         */
    }

    /*
    private void mainCycle() {
        IOrder order = new TestOrder();

        System.out.println("========== Tests ==========\n");

// uCard tests
        System.out.println("========== insert card, atm empty ==========\n");
        atm.insertCard(uCard);

        System.out.println("\n========== request balance ==========\n");
        atm.showBalance();

        System.out.println("\n========== order 1: debit, balance allows withdrawal ==========\n");
//        atm.queueOrder(order).show();
        atm.showBalance();

        System.out.println("\n========== order 2: debit, balance lower than price ==========\n");
//        atm.queueOrder(order).show();
        atm.showBalance();

        System.out.println("\n========== insert card, atm occupied with other card ==========\n");
        atm.insertCard(uCreditCard);

        System.out.println("\n========== eject card ==========\n");
        atm.ejectCurrentCard();

// uCreditCard tests
        System.out.println("\n========== insert new card into freed atm : credit, UniversalBank ==========\n");
        atm.insertCard(uCreditCard);

        System.out.println("\n========== request balance of new card ==========\n");
        atm.showBalance();

        System.out.println("\n========== order 3: credit, balance allows withdrawal once without credit ==========\n");
//        atm.queueOrder(order).show();
        atm.showBalance();

        System.out.println("\n========== order 4: credit, credit operations only ==========\n");
//        atm.queueOrder(order).show();
        atm.showBalance();
        atm.showCredit();

        System.out.println("\n========== order 5: credit, withdrawal out of credit limits ==========\n");
//        atm.queueOrder(order).show();
        atm.showBalance();
        atm.showCredit();

        System.out.println("\n========== eject card ==========\n");
        atm.ejectCurrentCard();

// commonCard tests
        System.out.println("\n========== insert new card: debit, SomeCommonBank ==========\n");
        atm.insertCard(commonCard);

        System.out.println("\n========== request balance ==========\n");
        atm.showBalance();

        System.out.println("\n========== order 6: debit, balance allows withdrawal ==========\n");
//        atm.queueOrder(order).show();
        atm.showBalance();

        System.out.println("\n========== order 7: debit, balance lower than price ==========\n");
//        atm.queueOrder(order).show();
        atm.showBalance();
        atm.showCredit();

        System.out.println("\n========== eject card ==========\n");
        atm.ejectCurrentCard();

// commonCreditCard tests
        System.out.println("\n========== insert new card: credit, SomeCommonBank ==========\n");
        atm.insertCard(commonCreditCard);

        System.out.println("\n========== request balance of new card ==========\n");
        atm.showBalance();

        System.out.println("\n========== order 8: credit, balance allows withdrawal once without credit ==========\n");
//        atm.queueOrder(order).show();
        atm.showBalance();

        System.out.println("\n========== order 9: credit, credit operations only ==========\n");
//        atm.queueOrder(order).show();
        atm.showBalance();
        atm.showCredit();

        System.out.println("\n========== order 10: credit, withdrawal out of credit limits ==========\n");
//        atm.queueOrder(order).show();
        atm.showBalance();
        atm.showCredit();

        System.out.println("\n========== show operation history ==========\n");
        atm.showHistory();

// show credit
        System.out.println("\n========== show current credit ==========\n");
        atm.showCredit();

// add money, to get rid of credit
        System.out.println("\n========== show current credit ==========\n");
        atm.addMoney(100.0);
        atm.showBalance();
        atm.showCredit();

        atm.addMoney(200.0);
        atm.showBalance();
        atm.showCredit();

        System.out.println();
        atm.showHistory();
    }

     */
}
