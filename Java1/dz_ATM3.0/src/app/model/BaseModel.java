package app.model;

import app.IATM;
import app.controller.Controller;
import app.controller.exceptions.AtmIsBusyException;
import app.controller.exceptions.CardBusyException;
import app.controller.exceptions.IllegalRequestSumException;
import app.controller.exceptions.IllegalRequestTypeException;
import app.model.bank.IBankRequest;
import app.model.bank.IBankResponse;
import app.model.bank.card.ICard;

import java.util.ArrayList;
import java.util.List;

public class BaseModel implements Model {

    protected Controller controller;
    protected IATM atm;

//    @Override
//    public boolean insertCard(ICard card) throws AtmIsBusyException {
//        return false;
//    }
//
//    @Override
//    public boolean ejectCard() throws CardBusyException {
//        return false;
//    }
//
//    @Override
//    public IBankResponse queueRequest(IBankRequest request) throws IllegalRequestTypeException, IllegalRequestSumException {
//        return null;
//    }
//
//    @Override
//    public ArrayList<ModelData> getMessages() {
//        return null;
//    }
//
//    @Override
//    public boolean processOkBtn() {
//        return false;
//    }
//
//    @Override
//    public void confirmMenuOptionSelect(MenuOption option) {
//
//    }
//
//    @Override
//    public List<MenuOption> getMenuOptions() {
//        return null;
//    }

    @Override
    public Model setController(Controller controller) {
        this.controller = controller;
        return this;
    }

    @Override
    public Model setATM(IATM atm) {
        this.atm = atm;
        return this;
    }
}