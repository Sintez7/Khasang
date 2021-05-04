package app;

import java.util.Arrays;
import java.util.Random;

public class LSB {

    public static final int SIZE = 10;
    private static final char INIT_SYMBOL = '.';
    private static final char HIT_POINT = '+';
    private static final char HIT_SHIP = 'x';

    Input input;

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
        this.input = input;
//        init();
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
            System.err.println("Введите число от 0 до " + SIZE);
            shoot(input.getShot());
            printField();
        }
    }

    public int shoot(int i) {
        int point = i - 1;
        if (point < 1 || point > SIZE) {
            System.err.println("invalid point: " + point);
            return 0;
        }

        shotCount++;

        if (point == curShipPos) {
            field[point] = HIT_SHIP;
            shipHit = true;
            System.err.println("Корабль потоплен!");
            System.err.println("Победа!");
            System.err.println("Количество ходов: " + shotCount);
            return 1;
        } else {
            field[point] = HIT_POINT;
            System.err.println("Промах!");
            return 2;
        }
    }

    private void printField() {
        System.err.println(field);
    }


    public int getShipPos() {
        return curShipPos;
    }
}
