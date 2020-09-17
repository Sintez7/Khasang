import java.util.ArrayList;
import java.util.List;

public class GameServer {

    private Player player1;
    private Player player2;

    private Field player1Field;
    private Field player2Field;

    private static final List<Player> spectators = new ArrayList<>();

    public GameServer() {

    }

    public void setPlayer1(Player player) {
        player1 = player;
    }

    public void setPlayer2(Player player) {
        player2 = player;
    }

    private void startGame() {
        init();
        start();
    }

    private void init() {

    }

    private void start() {

    }
}
