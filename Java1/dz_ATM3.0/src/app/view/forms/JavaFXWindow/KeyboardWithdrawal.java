package app.view.forms.JavaFXWindow;

public class KeyboardWithdrawal extends KeyboardController {
    @Override
    public void btnOkAction() {
        ATMMainWindow.getInstance().next();
    }
}
