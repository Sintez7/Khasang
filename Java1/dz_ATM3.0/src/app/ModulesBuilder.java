package app;

import app.controller.BaseController;
import app.controller.Controller;
import app.controller.ControllerAdapter;
import app.model.BaseModel;
import app.model.Model;
import app.model.ModelAdapter;
import app.view.BaseView;
import app.view.View;
import app.view.ViewAdapter;

public class ModulesBuilder {

    private View view = new ViewAdapter();
    private Model model = new ModelAdapter();
    private Controller controller = new ControllerAdapter();

    private IATM atm;

    private boolean changed = false;
    private boolean builded = false;

    public ModulesBuilder setActualView(BaseView view) {
        this.view = view;
        changed();
        return this;
    }

    public ModulesBuilder setActualModel(BaseModel model) {
        this.model = model;
        changed();
        return this;
    }

    public ModulesBuilder setActualController(BaseController controller) {
        this.controller = controller;
        changed();
        return this;
    }

    private void changed() {
        changed = true;
        builded = false;
    }

    public void build() {
        view.setController(controller);
        model.setController(controller).setATM(atm);
        controller.setModel(model).setView(view);

        builded = true;
        changed = false;
    }

    public Controller getController() {
        if (modulesReady()) {
            return controller;
        } else {
            System.err.println("module not ready, returning null");
            return null;
        }
    }

    public View getView() {
        if (modulesReady()) {
            return view;
        } else {
            System.err.println("module not ready, returning null");
            return null;
        }
    }

    public Model getModel() {
        if (modulesReady()) {
            return model;
        } else {
            System.err.println("module not ready, returning null");
            return null;
        }
    }

    private boolean modulesReady() {
        return !changed & builded;
    }

    public ModulesBuilder setATM(ATM atm) {
        this.atm = atm;
        changed();
        return this;
    }
}
