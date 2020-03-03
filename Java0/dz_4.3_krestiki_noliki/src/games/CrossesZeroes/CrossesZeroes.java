package games.CrossesZeroes;

import main.Game;
import main.GameLibrary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CrossesZeroes extends Game {

    private int gameID;
    private int fieldSize = 3;
    private int winCondition = 3;
    Field field;
    CrossesZeroesInputStrategy input; // Для тестов

    public CrossesZeroes(GameLibrary gameLibrary) {
        this(gameLibrary, null);
    }

    public CrossesZeroes(GameLibrary gameLibrary, CrossesZeroesInputStrategy input) {
        super("Crosses-Zeroes");
        gameID = gameLibrary.register(this);    // Получаем ID для этой игры и сохраняем его для дальнейшей работы
        if (input == null) {
            this.input = new DefaultInput();
        } else {
            this.input = input;
        }
    }

    @Override
    public void runGame() {
        initialiseGameField();
        boolean turn = false;
        int turnCounter = 1;
        boolean nextTurnPossible = true;

        field.showField();

        while (turnCounter <= fieldSize * fieldSize && nextTurnPossible) {
            System.out.println("Ходит " + (turn ? 2:1) + " игрок.");

            int result = -2;        // Инициализируем как -2 чтобы запустить первый цикл ввода данных игроком
            /*
             * Пока ввод игрока не принят - просить сделать ход
             */
            while(!(result == 0) && nextTurnPossible) {
                result = field.makeMove(input.move(), turn ? 1 : 2);        // Имитируем ход
                switch (result) {
                    case -2:
                        System.out.println("Клетка должна быть в игровом поле!");   // если вдруг координаты больше или меньш8е допустимых
                        continue;
                    case -1:
                        System.out.println("Эта клетка занята!");         // Надо повторить ход
                        continue;
                    // Каждый из следующих вариантов останавливает игру
                    case 1:
                        System.out.println("Игрок 1 победил!");
                        nextTurnPossible = false;
                        break;
                    case 2:
                        System.out.println("Игрок 2 победил!");
                        nextTurnPossible = false;
                        break;
                    default:        // Ход прошел нормально, выходим из цикла
                }
            }
            if (nextTurnPossible) {
                System.out.println(turnCounter + " ход завершен.");
                if (turnCounter == fieldSize * fieldSize) {
                    System.out.println("Ничья! Победила дружба!");
                }
                field.showField();
                turn = !turn;
                turnCounter++;
            } else {
                field.showField();
                // тут может быть предложение сыграть ещё раз, или статистика
            }
        }
    }

    private void initialiseGameField() {
        field = new Field(fieldSize, fieldSize, winCondition);
    }

    public int getGameID() {
        return gameID;
    }

    void setInputStrategy (CrossesZeroesInputStrategy input) {
        this.input = input;
    }
}

class Field {

    private Cell[][] field;
    private int winCondition = 0;
    private static String P1SIGN = "X";
    private static String P2SIGN = "O";
    private static int winConditionDefault = 3;
    private static final int[][] scanDirections =
            {{0, -1},
             {1, 0},
             {1, 1},
             {0, 1},
             {-1, 1},
             {-1, 0},
             {-1, -1}};

    Field() {
        this(0);
    }

    Field(int size) {
        this(size, size);
    }

    Field (int sizeX, int sizeY) {
        this(sizeX, sizeY, 0);
    }

    Field (int sizeX, int sizeY, int winCondition) {
        initField(sizeX, sizeY);
        if (winCondition == 0) {
            winCondition = winConditionDefault;
        } else {
            this.winCondition = winCondition;
        }
    }

    private void initField(int sizeX, int sizeY) {
        field = new Cell [sizeX + 1][sizeY + 1]; //добавляем места для координат слева и сверху
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                field[j][i] = new Cell(i, j, ".");
                if (i == 0 & j == 0) {
                    field[0][0].setContent(" ");
                } else {
                    if (i == 0) {
                        field[j][i].setContent(Integer.toString(j));
                    }
                    if (j == 0) {
                        field[j][i].setContent(Integer.toString(i));
                    }
                }
            }
        }
    }

    public Cell[][] getField() {
        return field;
    }

    void setWinCondition(int count) {
        this.winCondition = count;
    }

    void showField() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                System.out.print(field[j][i]);
            }
            System.out.println();
        }
    }

    int makeMove(int[] mC, int player) {
        Cell temp;
        if (mC[0] < 1 || mC[1] < 1 || mC[0] > field.length - 1 || mC[1] > field.length - 1) {
            return -2;     // Ячейка находится вне допустимых пределов
        } else {
            temp = field[mC[0]][mC[1]];
        }
        boolean successful;
        int checkResult;
        if (!temp.getContent().equals(P1SIGN) & !temp.getContent().equals(P2SIGN)) {
            field[mC[0]][mC[1]].setContent(player > 1 ? P1SIGN : P2SIGN);
            successful = true;
        } else {
            successful = false;
        }
        if (successful) {
            checkResult = checkWinCondition(mC[0], mC[1]);
        } else {
            return -1;             // Ячейка занята, надо сделать другой ход
        }
        switch (checkResult) {
            case 1:
                return 1;           // Выйграл 1 игрок
            case 2:
                return 2;           // Выйграл 2 игрок
            default:
                return 0;           // Игра может продолжаться
        }
    }

    private int checkWinCondition(int x, int y) {
        int result = scan(x, y);
        return result + 1;
    }

    /*
     * В этом методе строим 8 лучей от точки, и собираем что получилось в result
     * Если в result нету трёх последовательных одинаковых символов то возвращаем -1 : условий для победы нет
     * Если три символа принадлежат игроку 1 то возвращаем 0
     * Если три символа принадлежат игроку 2 то возвращаем 1
     */
    private int scan(int x, int y) {
        String temp;
        int vX;
        int vY;
        StringBuilder sbTemp1 = new StringBuilder();
        StringBuilder sbTemp2 = new StringBuilder();
        String p1win;
        String p2win;
        for (int i = 0; i < winCondition; i++) {
            sbTemp1.append(P1SIGN);
            sbTemp2.append(P2SIGN);
        }
        p1win = sbTemp1.toString();
        p2win = sbTemp2.toString();

        for (int i = 0; i < 7; i++) {
            vX = scanDirections[i][0];
            vY = scanDirections[i][1];
            temp = "";
            StringBuilder rsb = new StringBuilder();
            temp = scanNext(x, y,vX, vY, winCondition, rsb);
            if (temp.equals(p1win)) {
                return 0;
            } else {
                if (temp.equals(p2win)) {
                    return 1;
                } else {

                }
            }
        }
        return -1;
    }

    private String scanNext(int x, int y,int vX, int vY, int deep, StringBuilder sb) {
        int curX;
        int curY;
        for (int i = 0; i < deep; i++) {
            curX = x + (vX * i);
            curY = y + (vY * i);
            if (curX > 0 && curY > 0 && curX < field.length && curY < field.length) {
                sb.append(field[curX][curY].getContent());
            } else {
                return "";
            }
        }
        return sb.toString();
    }
}

/*
 * Класс созданный для теста ввода
 * Предполагается, что для ввода будет использоваться сторонний ввод,
 * потому здесь код только чтобы проверить работоспособность кода игры,
 * ответственность за ввод лежит на тестере.
 * Да, этот метод сломается при значении больше 9 или отрицательном,
 * но для теста достаточно.
 */
class DefaultInput implements CrossesZeroesInputStrategy {

    @Override
    public int[] move() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Формат ввода: x.y");
        String temp = "";
        try {
            temp = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int x = Integer.parseInt(temp.substring(0, 1));
        int y = Integer.parseInt(temp.substring(2, 3));
        return new int[]{x,y};
    }
}

class Cell {

    private int x;
    private int y;
    private String content;

    Cell(int xC, int yC, String content) {
        this.x = xC;
        this.y = yC;
        this.content = content;
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
        content = s;
    }

    @Override
    public String toString() {
        return " [" + content + "] ";
    }
}
