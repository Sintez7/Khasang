package app.view;

import app.controller.Controller;
import app.model.ModelData;

public interface View extends Runnable{

    void setController(Controller controller);
    void update(ModelData data);
}
