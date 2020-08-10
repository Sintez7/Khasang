package app.view.forms.JavaFXWindow;

import javafx.application.Platform;

import java.util.Timer;
import java.util.TimerTask;

public class Service {

    synchronized public static void callStarted() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.err.println("timer");
                Platform.runLater(() -> ATMMainWindow.getInstance().next());
            }
        }
        , 2000 + (Math.round(Math.random() * 1000)));
    }

}
