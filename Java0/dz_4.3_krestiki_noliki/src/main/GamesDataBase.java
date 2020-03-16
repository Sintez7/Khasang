package main;

public interface GamesDataBase {
    Game getGame(int id);
    int register(Game game);
    void show();
}
