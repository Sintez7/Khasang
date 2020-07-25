package app.view.forms.JavaFXWindow;

public class KeyboardAddMoney extends KeyboardController {
    @Override
    public void btnOkAction() {
        ATMMainWindow.getInstance().next();
    }

    @Override
    public void btnCancelAction() {
        super.btnCancelAction();
    }
}
