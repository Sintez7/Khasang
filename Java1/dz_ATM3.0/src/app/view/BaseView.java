package app.view;

import app.User;
import app.controller.Controller;
import app.model.MenuOption;
import app.model.bank.card.ICard;
import app.view.forms.JavaFXWindow.ATMMainWindow;

import java.util.List;

public class BaseView implements View {

    protected Controller controller;

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    public Controller getController() {
        return controller;
    }

    @Override
    public void startUp(User user) {
//        mainWindow.callLaunch(user);
        ATMMainWindow.callLaunch(user, controller);
    }
}
