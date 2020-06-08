package app.controller;

import app.controller.exceptions.*;
import app.model.bank.IBankRequest;
import app.model.bank.IBankResponse;
import app.model.bank.card.CardType;
import app.model.bank.card.ICard;

public interface Controller extends Runnable {

    /**
     * Метод для "вставки" карты в атм.
     * @param card
     * @throws AtmIsBusyException если в банкомат
     * попытаться вставить карту, когда в нём уже есть карта.
     */
    boolean insertCard(ICard card) throws AtmIsBusyException;

    boolean ejectCard() throws CardBusyException;

    DefaultController.RequestState isRequestReady();

    /*
     * метод synchronized
     * может вернуть null если запрос корявый, но ошибка не была выброшена
     */
    IBankResponse queueRequest(IBankRequest request) throws IllegalRequestTypeException, IllegalRequestSumException;

    Object getControllerKey();


}
