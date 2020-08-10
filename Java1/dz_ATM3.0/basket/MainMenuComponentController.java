package app.view.forms;

import java.net.URL;
import java.util.ResourceBundle;

import app.model.MenuOption;
import app.model.bank.card.ICard;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

public class MainMenuComponentController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private ListView<MenuOption> menuListView;

    private ObservableList<MenuOption> optionsList = FXCollections.observableArrayList();
    private JFXWindow mainClass;

    public static volatile MainMenuComponentController self;

    public MainMenuComponentController(JFXWindow jfxWindow) {
        initialize();
        self = this;
    }

    @FXML
    void initialize() {
//        assert rootPane != null : "fx:id=\"rootPane\" was not injected: check your FXML file 'mainMenuComponent.fxml'.";
//        assert menuListView != null : "fx:id=\"menuListView\" was not injected: check your FXML file 'mainMenuComponent.fxml'.";

        menuListView.setCellFactory(new Callback<ListView<MenuOption>, ListCell<MenuOption>>() {
            @Override
            public ListCell<MenuOption> call(ListView<MenuOption> iCardListView) {
                return new MenuOptionCell();
            }
        });
        menuListView.setItems(optionsList);
        JFXWindow.setMenuController(this);
    }

    void setMainClass (JFXWindow mainClass) {
        this.mainClass = mainClass;
    }

    void addMenuOption(MenuOption option) {
        optionsList.add(option);
    }

    private class MenuOptionCell extends ListCell<MenuOption>{

        @Override
        protected void updateItem(MenuOption option, boolean b) {
            super.updateItem(option, b);

            if (option != null) {
                setText(option.getDescription());
                mainClass.setSelectedOption(option);
            } else {
                System.err.println("option is null");
            }
        }
    }
}
