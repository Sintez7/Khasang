package app.view;

import app.User;
import app.model.bank.card.ICard;
import app.view.forms.JavaFXWindow.ATMMainWindow;

public class DefaultView extends BaseView {

    private ATMMainWindow mainWindow;

    @Override
    public void startUp(User user) {

        mainWindow = ATMMainWindow.getInstance();
        mainWindow.callLaunch(user);
    }
}
