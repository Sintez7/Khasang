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

public class ModelAdapter implements Model {

    private BaseModel actualModel;
    private IATM atm;

    public ModelAdapter() {
        this(null);
    }

    public ModelAdapter(BaseModel actualModel) {
        this.actualModel = actualModel;
    }

    public void setActualModel(BaseModel actualModel) {
        this.actualModel = actualModel;
    }

//    @Override
//    public boolean insertCard(ICard card) throws AtmIsBusyException {
//        return actualModel.insertCard(card);
//    }
//
//    @Override
//    public boolean ejectCard() throws CardBusyException {
//        return actualModel.ejectCard();
//    }
//
//    @Override
//    public IBankResponse queueRequest(IBankRequest request) throws IllegalRequestTypeException, IllegalRequestSumException {
//        return actualModel.queueRequest(request);
//    }
//
//    @Override
//    public ArrayList<ModelData> getMessages() {
//        return actualModel.getMessages();
//    }
//
//    @Override
//    public boolean processOkBtn() {
//        return actualModel.processOkBtn();
//    }
//
//    @Override
//    public void confirmMenuOptionSelect(MenuOption option) {
//        actualModel.confirmMenuOptionSelect(option);
//    }
//
//    @Override
//    public List<MenuOption> getMenuOptions() {
//        return actualModel.getMenuOptions();
//    }

    @Override
    public Model setController(Controller controller) {
        actualModel.setController(controller);
        return this;
    }

    @Override
    public Model setATM(IATM atm) {
        this.atm = atm;
        return this;
    }
}
