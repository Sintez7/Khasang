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
import app.view.View;

import java.util.ArrayList;
import java.util.List;

public class BaseController implements Controller {

    protected Model model;
    protected View view;

    @Override
    public boolean insertCard(ICard card) throws AtmIsBusyException {
        return false;
    }

    @Override
    public boolean ejectCard() throws CardBusyException {
        return false;
    }

    @Override
    public ModelData queueRequest(Order order) throws IllegalRequestTypeException, IllegalRequestSumException {
        return null;
    }

    @Override
    public Object getControllerKey() {
        return null;
    }

    @Override
    public ArrayList<ModelData> getModelData() {
        return null;
    }

    @Override
    public void setCards(ArrayList<ICard> cardsList) {

    }

    @Override
    public List<ICard> getCards() {
        return null;
    }

    @Override
    public void addCard(ICard card) {

    }

    @Override
    public boolean processOkBtn() {
        return false;
    }

    @Override
    public boolean processCancelBth() {
        return false;
    }

    @Override
    public List<MenuOption> getMenuOptions() {
        return null;
    }

    @Override
    public void confirmMenuOptionSelect(MenuOption option) {

    }

    @Override
    public Controller setModel(Model model) {
        this.model = model;
        return this;
    }

    @Override
    public Controller setView(View view) {
        this.view = view;
        return this;
    }
}
