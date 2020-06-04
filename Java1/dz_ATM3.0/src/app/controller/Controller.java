package app.controller;

import app.controller.exceptions.*;
import app.model.bank.IBankRequest;
import app.model.bank.IBankResponse;
import app.model.bank.card.CardType;
import app.model.bank.card.ICard;

public interface Controller extends Runnable {

    /**
     * Данный метод создаёт новую карту.
     * Она сразу готова к работе.
     * ВНИМАНИЕ! В текущий момент карта заполняется рандомным наполнением!
     * @param type - тип карты DEBIT и CREDIT
     * @return инстанс Card
     */
    ICard initNewCard(CardType type);

    /**
     * Метод для "вставки" карты в атм.
     * @param card
     * @throws AtmIsBusyException если в банкомат
     * попытаться вставить карту, когда в нём уже есть карта.
     */
    boolean insertCard(ICard card) throws AtmIsBusyException;

    boolean ejectCard() throws CardBusyException;

    IBankResponse queueRequest(IBankRequest request) throws IllegalRequestTypeException, IllegalRequestSumException;

    Object getControllerKey();
}
