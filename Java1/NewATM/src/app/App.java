package app;

import app.controller.Controller;
import app.controller.ControllerComponent;
import app.view.View;
import app.view.ViewComponent;
import javafx.application.Application;

public class App {

    public void start(String[] args) {
        System.err.println("App class " + Thread.currentThread());
        System.err.println("App_currentActiveThreads: " + Thread.activeCount());
        ControllerComponent controller = new Controller();
        Application.launch(app.view.View.class, args);
        System.err.println("App_currentActiveThreads: " + Thread.currentThread());
    }
}
