public class CrossesZeroes extends Game implements GameStrategy {

    private int gameID;

    CrossesZeroes(GameLibrary gameLibrary) {
        super("Crosses-Zeroes");
        gameID = gameLibrary.register(this);
    }

    @Override
    public String getName() {
        return getThisName();
    }

    @Override
    public void runGame() {
        System.out.println(getThisName() + " is running!");
    }

    @Override
    String getGameName() {
        return getThisName();
    }

    public int getGameID() {
        return gameID;
    }
}
