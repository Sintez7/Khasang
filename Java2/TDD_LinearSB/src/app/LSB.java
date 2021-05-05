package app;

import java.util.Arrays;
import java.util.Random;

public class LSB {

    public static final int SIZE = 10;
    private static final char INIT_SYMBOL = '.';
    private static final char HIT_POINT = '+';
    private static final char HIT_SHIP = 'x';

    public static final String MISS_MESSAGE = "Промах!";
    public static final String HIT_MESSAGE = "Корабль потоплен!";
    public static final String WIN_MESSAGE = "Победа!";
    public static final String MOVES_COUNT_MESSAGE = "Количество ходов: ";

    Input input;
    Output output;

    private char[] field = new char[SIZE];
    private int curShipPos = 0;
    private boolean shipHit = false;

    private boolean playable = false;

    private int shotCount = 0;

    private Random random = new Random();

    public LSB() {
        this(new ConsoleInput());
    }

    public LSB(Input input) {
        this(input, new SerrOut());
    }

    public LSB(Output output) {
        this(new ConsoleInput(), output);
    }

    public LSB(Input input, Output output) {
        this.input = input;
        this.output = output;
    }


    public char[] getField() {
        return Arrays.copyOf(field, SIZE);
    }

    public void init() {
        initField();
        curShipPos = random.nextInt(field.length);
        playable = true;
    }

    public void initField() {
        for (int i = 0; i < SIZE; i++) {
            field[i] = INIT_SYMBOL;
        }
    }

    public void play() {
        while(playable) {
            output.send("Введите число от 1 до " + SIZE);
            shoot(input.getShot());
            printField();
        }
    }

    public int shoot(int i) {
        if (i < 1 || i > SIZE) {
            output.send("invalid point: " + i);
            return 0;
        }

        int point = i - 1;
        shotCount++;

        if (point == curShipPos) {
            field[point] = HIT_SHIP;
            shipHit = true;
            output.send(HIT_MESSAGE);
            output.send(WIN_MESSAGE);
            output.send( MOVES_COUNT_MESSAGE + shotCount);
            playable = false;
            return 1;
        } else {
            field[point] = HIT_POINT;
            output.send(MISS_MESSAGE);
            return 2;
        }
    }

    private void printField() {
        System.err.println(field);
    }


    public int getShipPos() {
        return curShipPos;
    }

    public int getShotsCount() {
        return shotCount;
    }
}
