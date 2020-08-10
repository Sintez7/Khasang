package app.view;

import app.User;
import app.controller.Controller;
import app.model.MenuOption;
import app.model.ModelData;
import app.model.bank.IBankResponse;
import app.model.bank.card.ICard;

import java.util.List;

public interface View{

    void setController(Controller controller);

    void startUp(User user);

    void callbackResult(IBankResponse result);

    void callTimeout();
}
