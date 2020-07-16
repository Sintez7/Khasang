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

public interface Model {

    boolean insertCard(ICard card) throws AtmIsBusyException;

    boolean ejectCard() throws CardBusyException;

    IBankResponse queueRequest(IBankRequest request) throws IllegalRequestTypeException, IllegalRequestSumException;

    ArrayList<ModelData> getMessages();

    boolean processOkBtn();

    void confirmMenuOptionSelect(MenuOption option);

    List<MenuOption> getMenuOptions();

    Model setController(Controller controller);

    Model setATM(IATM atm);
}
