package app.view;

import app.model.ModelData;
import app.view.forms.NewForm;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DefaultWindow extends JFrame implements Runnable {

    private final Object key = new Object();

    private final static int MINIMUM_WIDTH = 600;
    private final static int MINIMUM_HEIGHT = 400;
    private final static int DEFAULT_WIDTH = 800;
    private final static int DEFAULT_HEIGHT = 600;

    private NewForm mainForm = new NewForm();

    public DefaultWindow() {
        super();
        Dimension minimumSize = new Dimension(MINIMUM_WIDTH, MINIMUM_HEIGHT);
        setMinimumSize(minimumSize);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        setLocationRelativeTo(null);
        setTitle("ATM prop");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void run() {
        showMainFrame();
//        try {
//            synchronized (key) {
//                key.wait();
//            }
//        } catch (InterruptedException e) {}
    }

    public void updateContent(ArrayList<ModelData> data) {
        mainForm.updateContent(data);
    }

    private void showMainFrame() {
        setContentPane(mainForm.getRootPanel());
        setVisible(true);
//        mainForm.focusField();
    }
}
