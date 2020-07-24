package app.view.forms.JavaFXWindow;

public class KeyboardCardSelect extends KeyboardController{

    private ATMMainWindow mainWindow;

    public KeyboardCardSelect (ATMMainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    @Override
    public void btnOkAction() {
        mainWindow.cardChosen();
    }
}
