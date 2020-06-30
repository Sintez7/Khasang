package app.model;

import app.IATM;
import app.controller.exceptions.*;
import app.model.bank.IBankRequest;
import app.model.bank.IBankResponse;
import app.model.bank.card.ICard;

import java.util.ArrayList;

public class DefaultModel implements Model {

    private final Object modelMainKey = new Object();
    private IATM atm;
    private StateMachine sm = new StateMachine(new PreparingState());

    private ArrayList<ModelData> data;

    public DefaultModel (IATM atm) {
        this.atm = atm;
        sm.execute(Command.INIT);
    }

    public void addData(ModelData message) {
        data.add(message);
    }

    @Override
    public ArrayList<ModelData> getMessages() {
        return data;
    }

    @Override
    public boolean processOkBtn() {
        sm.execute(Command.OK);
        return true;
    }

    @Override
    public void run() {
        try {
            synchronized (modelMainKey) {
                modelMainKey.wait();
            }
        } catch (InterruptedException e) {}
    }

    @Override
    public boolean insertCard(ICard card) throws AtmIsBusyException {
        boolean temp = atm.insertCard(card);
        if (temp) {
            sm.execute(Command.CARD_INSERTED);
        }
        return temp;
    }

    @Override
    public boolean ejectCard() throws CardBusyException {
        return atm.ejectCurrentCard();
    }

    @Override
    public IBankResponse queueRequest(IBankRequest request) throws IllegalRequestTypeException, IllegalRequestSumException {
        return atm.queueOrder(request);
    }

    private class StateMachine {

        State prevState = null;
        State currentState;

        StateMachine (State state) {
            currentState = state;
        }

        void execute(Command command) {
            prevState = currentState;
            currentState = currentState.execute(command);
        }
    }

    enum Command {
        INIT,
        OK,
        CANCEL,
        CARD_INSERTED
    }

    enum StateType {
        PREPARING,
        READY,
        WORKING_MAIN_MENU,
        CARD_BUSY
    }

    public class MainMenuOption {

    }

    private abstract class State {
        static final String UNKNOWN_COMMAND_MSG = "Unknown Command";
        static final String ATM_READY_MSG = "ATM is ready! \nPlease insert Card.";
        static final String WELCOME_MSG = "Welcome! Please insert card.";

        StateType type = StateType.PREPARING;
        String message = "";

        abstract State execute(Command command);

        public StateType getType() {
            return type;
        }
        public String getMessage() {
            return message;
        }

        State setMessage(String msg) {
            message = msg;
            return this;
        }
    }

    class PreparingState extends State {

        PreparingState() {
            type = StateType.PREPARING;
        }

        @Override
        State execute(Command command) {
            return new ReadyState().setMessage(WELCOME_MSG);
        }
    }

    private class ReadyState extends State {

        ReadyState() {
            type = StateType.READY;
        }

        @Override
        State execute(Command command) {
            switch (command) {
                case OK:
                    return processOkCommand();
                case CANCEL:
                    return processCancelCommand();
                case CARD_INSERTED:
                    return processCardInserted();
                default:
                    message = UNKNOWN_COMMAND_MSG;
                    return this;
            }
        }

        private State processCardInserted() {
            return new MainMenuState();
        }

        private State processOkCommand() {
            message = ATM_READY_MSG;
            return this;
        }

        private State processCancelCommand() {
            message = ATM_READY_MSG;
            return this;
        }
    }

    class MainMenuState extends State {

        MainMenuState() {
            type = StateType.WORKING_MAIN_MENU;
        }

        @Override
        State execute(Command command) {
            switch (command) {
                case OK:
                    return processOkCommand();
                case CANCEL:
//                    return processCancelCommand();
                default:
                    message = UNKNOWN_COMMAND_MSG;
                    return this;
            }
        }

        private State processOkCommand() {
            return null;
        }
    }
}
