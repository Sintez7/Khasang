package main;

public class GameLibrary{

    // Собсно, объект содержащий ссылки на все игры
    private GamesDataBase db;

    // Заполнением занимается метод register()
    GameLibrary () {
        this(null);
    }

    GameLibrary (GamesDataBase db) {
        if (db == null) {
            this.db = new DefaultGamesDataBase();
        } else {
            this.db = db;
        }
    }

    public int register(Game game) {
        return db.register(game);
    }

    public GameStrategy pickGame(int id) {
        return db.getGame(id);
    }

    public void show() {
        db.show();
    }
}
