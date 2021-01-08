package app.shared;

public class TurnUpdate extends DataPackage {

    private final int[][] playerField;
    private final int[][] opponentField;
    private final int playerTurn;

    public TurnUpdate(int[][] playerField, int[][] opponentField, int playerTurn) {
        super(DataPackage.TURN_UPDATE);

        this.playerField = new int[playerField.length][playerField.length];
        for (int i = 0; i < this.playerField.length; i++) {
            for (int j = 0; j < 10; j++) {
                this.playerField[j][i] = playerField[j][i];
            }
        }

        this.opponentField = new int[playerField.length][playerField.length];
        for (int i = 0; i < this.opponentField.length; i++) {
            for (int j = 0; j < 10; j++) {
                this.opponentField[j][i] = opponentField[j][i];
            }
        }

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
