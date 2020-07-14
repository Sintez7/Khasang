package atmapp;

public class KeyboardCardSelect extends KeyboardController{

    @Override
    public void btnOkAction() {
        System.err.println("KeyboardCardSelect OkAction");
        Service.cardChosen();
    }
}
