package main;

import java.util.ArrayList;
import java.util.List;

public class DefaultGamesDataBase implements GamesDataBase {

    List<Game> gameLibrary = new ArrayList<>();
    private int gameCounter = 0;

    @Override
    public Game getGame(int id) {
        for (Game g : gameLibrary) {
            if (g.getGameID() == id) {
                return g;
            }
        }
        return null;
    }

    @Override
    public int register(Game game) {
        gameLibrary.add(game);
        return gameCounter++;
    }

    @Override
    public void show() {
        for (Game g : gameLibrary) {
            System.out.println("#" + g.getGameID() + ": " + g.getName());
        }
    }
}
