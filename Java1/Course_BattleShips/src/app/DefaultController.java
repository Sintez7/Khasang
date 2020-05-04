package app;

public class DefaultController implements Controller {

    private Model model;

    @Override
    public void setModel(Model model) {
        this.model = model;
    }

    @Override
    public void invokeFirstLaunch() {

    }

    @Override
    public void focus() {

    }
}
