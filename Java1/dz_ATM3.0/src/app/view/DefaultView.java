package app.view;

import app.controller.Controller;
import app.view.forms.NewForm;

import javax.swing.*;

public class DefaultView implements View {

    private static final DefaultWindow WINDOW = new DefaultWindow();
    private Controller controller;

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void update() {
        WINDOW.updateContent(controller.getModelData());
    }

    @Override
    public void run() {
        SwingUtilities.invokeLater(WINDOW);
    }
}
