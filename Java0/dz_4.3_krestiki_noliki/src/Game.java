public abstract class Game {
    private String gameName;

    Game(String gameName) {
        this.gameName = gameName;
    }

    String getThisName() {
        return gameName;
    }

    abstract String getGameName();
}
