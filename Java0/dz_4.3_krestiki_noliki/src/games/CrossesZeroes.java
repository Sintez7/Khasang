package games;

import main.Game;
import main.GameLibrary;

public class CrossesZeroes extends Game {

    private int gameID;
    private int fieldSize = 3;
    private static String P1SIGN = "X";
    private static String P2SIGN = "O";
    Cell[][] field;

    public CrossesZeroes(GameLibrary gameLibrary) {
        super("Crosses-Zeroes");
        gameID = gameLibrary.register(this);    // Получаем ID для этой игры и сохраняем его для дальнейшей работы
    }

    @Override
    public void runGame() {
        initialiseGameField();
        boolean turn = true;
        int turnCounter = 1;

        while (turnCounter <= 9) {
            showField();
            System.out.println("Player " + (turn ? 1:2) + " turn");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}
            makeTurnPlaceholder(turn);
            System.out.println(turnCounter + " game turn is done");
            turn = !turn;
            turnCounter++;
        }
    }

    private void showField() {
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                System.out.print(field[j][i]);
            }
            System.out.println();
        }
    }

    private void makeTurnPlaceholder(boolean player) {
        String playerSign;
        if (player) {
            playerSign = P1SIGN;
        } else {
            playerSign = P2SIGN;
        }
        Cell move = getMove();
        int xCoordinate = move.getX();
        int yCoordiante = move.getY();

//        field[xCoordinate][yCoordiante] = " [" + playerSign + "] ";
    }

    private Cell getMove() {

        return null;
    }

    private void initialiseGameField() {
        field = new Cell[fieldSize][fieldSize];
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                field[j][i] = new Cell(j,i,".");
            }
        }
    }

    public int getGameID() {
        return gameID;
    }
}

class Cell {

    private int x;
    private int y;
    private String content;

    Cell(int xC, int yC, String content) {
        this.x = xC;
        this.y = yC;
        this.content = " [" + content + "] ";
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String s) {
        content = " [" + s + "] ";
    }

    @Override
    public String toString() {
        return content;
    }
}
