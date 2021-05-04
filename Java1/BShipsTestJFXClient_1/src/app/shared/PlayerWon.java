package app.shared;

public class PlayerWon extends DataPackage {

    private int playerWon;

    public PlayerWon(int playerWon) {
        super(PLAYER_WON);
        this.playerWon = playerWon;
    }

    public int getPlayerWon() {
        return playerWon;
    }
}
