package app;

import java.util.ArrayList;
import java.util.List;

public class GameServer extends Thread {

    private Player player1;
    private Player player2;

    private Field player1Field;
    private Field player2Field;

    private final List<Player> spectators = new ArrayList<>();

    public GameServer(Player player1, Player player2, List<Player> spectators) {
        this.player1 = player1;
        this.player2 = player2;
        this.spectators.addAll(spectators);
    }

    public void setPlayer1(Player player) {
        player1 = player;
    }

    public void setPlayer2(Player player) {
        player2 = player;
    }

    public GameServer startGame() {
        start();
        return this;
    }

    @Override
    public void run() {

    }

//    private void startGame() {
//        init();
//        launchGame();
//    }

    private void init() {

    }

    private void launchGame() {

    }
}
