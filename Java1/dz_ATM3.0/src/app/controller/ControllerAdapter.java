package app.controller;

import app.Order;
import app.controller.exceptions.AtmIsBusyException;
import app.controller.exceptions.CardBusyException;
import app.controller.exceptions.IllegalRequestSumException;
import app.controller.exceptions.IllegalRequestTypeException;
import app.model.MenuOption;
import app.model.Model;
import app.model.ModelData;
import app.model.bank.card.ICard;

import java.util.ArrayList;
import java.util.List;

public class ControllerAdapter implements Controller {

    private BaseController actualController;

    public ControllerAdapter() {
        this(null);
    }

    public ControllerAdapter(BaseController actualController) {
        this.actualController = actualController;
    }

    public void setActualController(BaseController actualController) {
        this.actualController = actualController;
    }

    @Override
    public boolean insertCard(ICard card) throws AtmIsBusyException {
        return actualController.insertCard(card);
    }

    @Override
    public boolean ejectCard() throws CardBusyException {
        return actualController.ejectCard();
    }

    @Override
    public ModelData queueRequest(Order order) throws IllegalRequestTypeException, IllegalRequestSumException {
        return actualController.queueRequest(order);
    }

    @Override
    public Object getControllerKey() {
        return actualController.getControllerKey();
    }

    @Override
    public ArrayList<ModelData> getModelData() {
        return actualController.getModelData();
    }

    @Override
    public void setCards(ArrayList<ICard> cardsList) {
        actualController.setCards(cardsList);
    }

    @Override
    public List<ICard> getCards() {
        return actualController.getCards();
    }

    @Override
    public void addCard(ICard card) {
        actualController.addCard(card);
    }

    @Override
    public boolean processOkBtn() {
        return actualController.processOkBtn();
    }

    @Override
    public boolean processCancelBth() {
        return actualController.processCancelBth();
    }

    @Override
    public List<MenuOption> getMenuOptions() {
        return actualController.getMenuOptions();
    }

    @Override
    public void confirmMenuOptionSelect(MenuOption option) {
        actualController.confirmMenuOptionSelect(option);
    }

    @Override
    public Controller setModel(Model model) {
        actualController.setModel(model);
        return null;
    }
}
