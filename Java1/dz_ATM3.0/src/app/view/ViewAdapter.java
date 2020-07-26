package app.view;

import app.User;
import app.controller.Controller;
import app.model.MenuOption;
import app.model.bank.IBankResponse;
import app.model.bank.card.ICard;

import java.util.List;

public class ViewAdapter implements View {

    private BaseView actualView;

    public ViewAdapter() {
        this(null);
    }

    public ViewAdapter(BaseView actualView) {
        this.actualView = actualView;
    }

    public ViewAdapter setActualView(BaseView actualView) {
        this.actualView = actualView;
        return this;
    }

    @Override
    public void setController(Controller controller) {
        if (actualView != null) {
            actualView.setController(controller);
        } else {
            System.err.println("actualView is null!");
        }
    }

    @Override
    public void startUp(User user) {
        actualView.startUp(user);
    }

    @Override
    public void callbackResult(IBankResponse result) {
        actualView.callbackResult(result);
    }

    @Override
    public void callTimeout() {
        actualView.callTimeout();
    }

//    @Override
//    public void update(List<MenuOption> options) {
//        if (actualView != null) {
//            actualView.update(options);
//        } else {
//            System.err.println("actualView is null!");
//        }
//    }
//
//    @Override
//    public void addCard(ICard card) {
//        if (actualView != null) {
//            actualView.addCard(card);;
//        } else {
//            System.err.println("actualView is null!");
//        }
//    }
}
