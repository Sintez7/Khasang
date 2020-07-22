package app.view;

import app.User;
import app.view.forms.JavaFXWindow.ATMMainWindow;

public class DefaultView extends BaseView {

//    private ATMMainWindow mainWindow;

    @Override
    public void startUp(User user) {
//        mainWindow.callLaunch(user);
        ATMMainWindow.callLaunch(user, controller);
    }
}
