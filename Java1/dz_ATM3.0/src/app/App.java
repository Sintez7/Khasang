package app;

import app.controller.DefaultController;
import app.model.DefaultModel;
import app.model.bank.banks.SomeCommonBank;
import app.model.bank.banks.UniversalBank;
import app.model.bank.card.CardType;
import app.model.bank.card.ICard;
import app.view.DefaultView;

public class App {

//    private Model model;
//    private View view;
//
//    private IBank uBank;
//    private IBank commonBank;
//
//    private ICard uCard;
//    private ICard uCreditCard;
//    private ICard commonCard;
//    private ICard commonCreditCard;

    public static final Object monitor = new Object();

    public void init() {
        ModulesBuilder mb = new ModulesBuilder();

        UniversalBank uBank = new UniversalBank();
        SomeCommonBank commonBank = new SomeCommonBank();

        mb.setActualController(new DefaultController());
        DefaultModel dModel = new DefaultModel();
        mb.setActualModel(dModel);
        mb.setActualView(new DefaultView());

        mb.setATM(new ATM(uBank, dModel));

        mb.build();

        ICard uCard = uBank.initNewCard(CardType.DEBIT);
        ICard uCreditCard = uBank.initNewCard(CardType.CREDIT);

        ICard commonCard = commonBank.initNewCard(CardType.DEBIT);
        ICard commonCreditCard = commonBank.initNewCard(CardType.CREDIT);

        User user = new User();
        user.addCard(uCard);
        user.addCard(uCreditCard);
        user.addCard(commonCard);
        user.addCard(commonCreditCard);

        mb.startUp(user);
    }

    public void start() {
//        mainCycle();
//        new Thread(controller).start();
//        new Thread(model).start();
//        Thread viewThread = new Thread(view);
//        viewThread.start();


//        ICard uCard = uBank.initNewCard(CardType.DEBIT);
//        ICard uCreditCard = uBank.initNewCard(CardType.CREDIT);
//
//        ICard commonCard = commonBank.initNewCard(CardType.DEBIT);
//        ICard commonCreditCard = commonBank.initNewCard(CardType.CREDIT);
//
//        controller.addCard(uCard);
//        controller.addCard(uCreditCard);
//        controller.addCard(commonCard);
//        controller.addCard(commonCreditCard);
//        System.err.println("cards added");

        System.err.println("joining viewThread");
        synchronized (monitor) {
            try {
                monitor.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.err.println("viewThread joined");

//        view.setController(controller);
//        System.err.println("setted controller for view");



        runRandomTests();

        synchronized (monitor) {
            try {
                monitor.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void initModules() {

//        atm = new ATM(uBank);
//        System.err.println("created start modules");
//
//        model = new DefaultModel(atm);
//        System.err.println("created Model");
//        //        ((JFXWindow) mainWindow).myLaunch(args);
//        view = new JFXWindow();
//        System.err.println("view created");
//        controller = new DefaultController(model, atm, view);
//        System.err.println("controller created");
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
