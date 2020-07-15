package atmapp;

public class KeyboardAddMoney extends KeyboardController {
    @Override
    public void btnOkAction() {
        Service.callNext();
    }

    @Override
    public void btnCancelAction() {
        super.btnCancelAction();
    }
}
