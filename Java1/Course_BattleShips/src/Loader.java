import app.BattleShips;
import app.IGame;

public class Loader {

    public static void main(String[] args) {
        IGame game = new BattleShips();
        game.launch();
    }
}
