import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Dz {

    public static void main(String[] args) {
        /*
         * // свободные ячейки обозначим знаком ?
         * cells = new char[][]{{'?', '?', '?'}, {'?', '?', '?'}, {'?', '?', '?'}};
         * generateRandomXO();
         * // выведем на экран
         * System.out.println(Arrays.deepToString(cells));
         *
         */

        Game game = new Game();
        game.startGame();
    }

    /*
    private static void generateRandomXO() {
        int rowRnd;
        int colRnd;
        do
        {
            // определили случайные координаты
            rowRnd = (int) (Math.random() * 3);
            colRnd = (int) (Math.random() * 3);
        } while (mainField[rowRnd][colRnd] != '?'); // продолжать пока не найдем свободную ячейку
        mainField[rowRnd][colRnd] = 'X';
    }
     */
}

class Game {

    private static final int DEFAULT_X_LENGTH = 3;
    private static final int DEFAULT_Y_LENGTH = 7;

    private static final char EMPTY_CELL_SIGN = '?';

    public Cell[][] mainField = new Cell[DEFAULT_X_LENGTH][DEFAULT_Y_LENGTH];
    private List<Cell> availableCells = new ArrayList<>();

    public void startGame() {

        int turnCounter = 0;

        initField();
        createAvailableCellsList();

        Player player = new Player(PlayerType.AI);

        do
        {
            turnCounter++;
            showField();
            processMove(player);
        } while (turnCounter < DEFAULT_X_LENGTH * DEFAULT_Y_LENGTH);

        showResults();
    }

    private void initField() {

        // Инициализация массива поля в формате x.y
        for (int i = 0; i < DEFAULT_X_LENGTH; i++) {
            for (int j = 0; j < DEFAULT_Y_LENGTH; j++) {
                mainField[i][j] = new Cell(i, j, EMPTY_CELL_SIGN);
            }
        }
    }

    private void createAvailableCellsList() {

        // Потрошим поле в коллекцию List, в которой будут находиться свободные ячейки.
        for (int i = 0; i < DEFAULT_X_LENGTH; i++) {
            for (int j = 0; j < DEFAULT_Y_LENGTH; j++) {
                availableCells.add(mainField[i][j]);
            }
        }
    }

    private void showField() {
        for (int i = 0; i < DEFAULT_Y_LENGTH; i++) {
            for (int j = 0; j < DEFAULT_X_LENGTH; j++) {
                System.out.print(mainField[j][i]); // отображение в формате x.y

            }
            System.out.println();
        }
        System.out.println();
    }

    private void processMove(Player player) {
        Cell temp = player.makeMove(availableCells);

        if (temp != null) {
            do
            {
                if (mainField[temp.getX()][temp.getY()].content == EMPTY_CELL_SIGN) {
                    mainField[temp.getX()][temp.getY()].content = player.getPlayerSign();
                    availableCells.remove(temp);
                    break;
                } else {
                    System.out.println("Cell is not available!");
                }
            } while (true);
        }
    }

    private void showResults() {
        showField();
        System.out.println("Game finished!");
    }

}

class Cell {
    private int x;
    private int y;
    public char content;

    Cell(int x, int y, char content) {
        this.x = x;
        this.y = y;

        this.content = content;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "[" + content + "]";
    }
}

class Player {

    PlayerType type;
    private char playerSign;

    Player(PlayerType type) {
        this.type = type;
        playerSign = 'X';
    }

    char getPlayerSign() {
        return playerSign;
    }

    Cell makeMove(List<Cell> availableCells) {
        switch (this.type) {
            case AI:
                return makeAIMove(availableCells);
            case PLAYER:
                return makePlayerMove(availableCells);
        }
        return null;
    }

    private Cell makeAIMove(List<Cell> availableCells) {
        if (availableCells.size() >= 1) {
            Random rand = new Random();
            return availableCells.get(rand.nextInt(availableCells.size()));
        } else {
            System.out.println("No more move available!");
            return null;
        }
    }

    private Cell makePlayerMove(List<Cell> availableCells) {
        System.out.println("Player done");
        return null;
    }

}

enum PlayerType {
    AI,
    PLAYER
}