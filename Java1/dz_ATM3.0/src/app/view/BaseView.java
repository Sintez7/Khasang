package app.view;

import app.controller.Controller;
import app.model.MenuOption;
import app.model.bank.card.ICard;

import java.util.List;

public abstract class BaseView implements View {

    @Override
    public void setController(Controller controller) {

    }

    @Override
    public void update(List<MenuOption> options) {

    }

    @Override
    public void addCard(ICard card) {

    }
}
