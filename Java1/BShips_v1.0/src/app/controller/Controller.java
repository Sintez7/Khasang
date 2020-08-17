package app.controller;

import app.Main;
import app.model.Model;

public class Controller {

    private Main main;
    private Model model;

    public Controller(Main main) {
        this.main = main;
    }

    public void moveDone() {

    }

    public void setModel(Model model) {
        this.model = model;
    }
}
