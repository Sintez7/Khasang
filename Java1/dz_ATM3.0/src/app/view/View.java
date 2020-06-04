package app.view;

import app.controller.Controller;

public interface View extends Runnable{

    void setController(Controller controller);
}
