package app.view;

import app.controller.Controller;
import app.model.ModelData;
import app.model.bank.card.ICard;

public interface View extends Runnable{

    void setController(Controller controller);
    void update(ModelData data);

    void addCard(ICard card);
}
