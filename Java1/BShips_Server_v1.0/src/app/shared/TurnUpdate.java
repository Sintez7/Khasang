package app.shared;

public class TurnUpdate extends DataPackage {

    private final int[][] playerField;
    private final int[][] opponentField;
    private final int playerTurn;

    public TurnUpdate(int[][] playerField, int[][] opponentField, int playerTurn) {
        super(DataPackage.TURN_UPDATE);
        this.playerField = playerField;
        this.opponentField = opponentField;
        this.playerTurn = playerTurn;
    }

    public int[][] getPlayerField() {
        return playerField;
    }

    public int[][] getOpponentField() {
        return opponentField;
    }

    public int getPlayerTurn() {
        return playerTurn;
    }
}
