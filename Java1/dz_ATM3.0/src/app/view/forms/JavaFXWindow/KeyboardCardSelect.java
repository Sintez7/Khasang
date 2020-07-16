package app.view.forms.JavaFXWindow;

public class KeyboardCardSelect extends KeyboardController{

    @Override
    public void btnOkAction() {
        System.err.println("KeyboardCardSelect OkAction");
        Service.callNext();
    }
}
