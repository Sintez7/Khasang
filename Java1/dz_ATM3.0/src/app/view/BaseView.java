package app.view;

import app.User;
import app.controller.Controller;
import app.model.MenuOption;
import app.model.bank.card.ICard;

import java.util.List;

public abstract class BaseView implements View {

    protected Controller controller;

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    public Controller getController() {
        return controller;
    }

    @Override
    public abstract void startUp(User user);
}