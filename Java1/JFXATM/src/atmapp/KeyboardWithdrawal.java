package atmapp;

public class KeyboardWithdrawal extends KeyboardController {
    @Override
    public void btnOkAction() {
        Service.callNext();
    }
}
