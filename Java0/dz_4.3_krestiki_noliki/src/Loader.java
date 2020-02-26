public class Loader {
    private static GameLibrary gameLibrary;

    public static void main(String[] args) {
        startupInitialize();
        GameStrategy strategy = gameLibrary.pickGame();
        strategy.runGame();
    }

    private static void startupInitialize() {
        gameLibrary = new GameLibrary();
        createGames();
    }

    private static void createGames() {
        CrossesZeroes cz = new CrossesZeroes(gameLibrary);
    }
}
