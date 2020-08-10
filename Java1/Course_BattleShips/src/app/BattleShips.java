package app;

public class BattleShips implements Game {

    private Model model;
    private View view;
    private Controller controller;

    @Override
    public void launch() {
        init(new DefaultModel(), new DefaultView(), new DefaultController());

        startMainCycle();
    }

    private void init(Model model, View view, Controller controller) {
        this.model = model;
        this.view = view;
        this.controller = controller;
        controller.setModel(model);
        view.setModel(model);
    }

    private void startMainCycle() {
        controller.invokeFirstLaunch();
        while (model.isGameEnded()) {
            model.update();
            view.update();
            controller.focus();
        }
    }
}
