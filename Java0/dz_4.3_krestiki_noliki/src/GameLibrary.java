import java.util.ArrayList;

public class GameLibrary{

    private ArrayList<Game> gameLibrary;

    GameLibrary () {
        gameLibrary = new ArrayList<>();
    }

    public int register(Game game) {
        return processRegistration(game);
    }

    private int processRegistration (Game game) { //throws GameRegistrationException
        gameLibrary.add(game);
        return gameLibrary.size();
    }

    GameStrategy pickGame() {
        for (Game g : gameLibrary) {
            System.out.println(g.getGameName());
        }
        return (GameStrategy) gameLibrary.get(askUserToPickGame());
    }

    private int askUserToPickGame() {
        return 0;
    }
}
