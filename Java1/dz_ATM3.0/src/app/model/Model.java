package app.model;

import app.controller.exceptions.AtmIsBusyException;
import app.controller.exceptions.CardBusyException;
import app.controller.exceptions.IllegalRequestSumException;
import app.controller.exceptions.IllegalRequestTypeException;
import app.model.bank.IBankRequest;
import app.model.bank.IBankResponse;
import app.model.bank.card.ICard;

import java.util.ArrayList;
import java.util.List;

public interface Model extends Runnable{

    boolean insertCard(ICard card) throws AtmIsBusyException;

    boolean ejectCard() throws CardBusyException;

    IBankResponse queueRequest(IBankRequest request) throws IllegalRequestTypeException, IllegalRequestSumException;

    ArrayList<ModelData> getMessages();

    boolean processOkBtn();

    void confirmMenuOptionSelect(MenuOption option);

    List<MenuOption> getMenuOptions();
}
