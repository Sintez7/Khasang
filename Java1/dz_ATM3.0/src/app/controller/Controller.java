package app.controller;

import app.Order;
import app.controller.exceptions.*;
import app.model.MenuOption;
import app.model.Model;
import app.model.ModelData;
import app.model.bank.card.ICard;
import app.view.View;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public interface Controller {

    /**
     * Метод для "вставки" карты в атм.
     * @param card
     * @throws AtmIsBusyException если в банкомат
     * попытаться вставить карту, когда в нём уже есть карта.
     */
    boolean insertCard(ICard card) throws AtmIsBusyException;

    boolean ejectCard() throws CardBusyException;

     // synchronized
    ModelData queueRequest(Order order) throws IllegalRequestTypeException, IllegalRequestSumException;

    Object getControllerKey();

    ArrayList<ModelData> getModelData();

    void setCards(ArrayList<ICard> cardsList);

    List<ICard> getCards();

    void addCard(ICard card);

    boolean processOkBtn();

    boolean processCancelBth();

    List<MenuOption> getMenuOptions();

    void confirmMenuOptionSelect(MenuOption option);

    Controller setModel(Model model);

    Controller setView(View view);
}
