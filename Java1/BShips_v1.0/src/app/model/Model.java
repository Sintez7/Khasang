package app.model;

import app.controller.Controller;
import app.Main;
import app.player.Player;

public class Model {

    private Main main;
    private Controller controller;

    private Player playerOne;
    private Player playerTwo;

    private Field playerOneField;
    private Field playerTwoField;

    public Model(Main main) {
        this.main = main;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public Model setPlayerOne(Player player) {
        playerOne = player;
        return this;
    }

    public Model setPlayerTwo(Player player) {
        playerTwo = player;
        return this;
    }

    public Model setPlayerOneField (Field field) {
        playerOneField = field;
        return this;
    }

    public Model setPlayerTwoField (Field field) {
        playerTwoField = field;
        return this;
    }


}
