package app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Field {

    private static final int FREE = 0;
    private static final int OCCUPIED = 1;
    private static final int HIT = 2;

    private static final int SIZE = 10;
    private static final List<ActualShip> shipsInPlay = new ArrayList<>();

    private static final int[][] SCAN_DIRECTIONS =
            {{0, -1},
                    {1, -1},
                    {1, 0},
                    {1, 1},
                    {0, 1},
                    {-1, 1},
                    {-1, 0},
                    {-1, -1}};

    int[][] cells = new int[SIZE][SIZE];

    public Field() {
        initField();
    }

    private void initField() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                cells[j][i] = FREE;
            }
        }
    }

    public void hit(int x, int y) {
        if (cells[x - 1][y - 1] != HIT) {
            if (hitPoint(x - 1, y - 1)) {
                checkLose();
//                server.hit();
            }
//            server.miss();
        } else {
//            server.pointHit();
        }
    }

    private boolean hitPoint(int x, int y) {
        cells[x][y] = HIT;
        return hitShip(x, y);
    }

    private boolean hitShip(int x, int y) {
        for (ActualShip actualShip : shipsInPlay) {
            if (actualShip.hit(x, y)) {
                return true;
            }
        }
        return false;
    }

    private void checkLose() {
        boolean defeated = true;
        for (ActualShip actualShip : shipsInPlay) {
            if (actualShip.isAlive()) {
                defeated = false;
                break;
            }
        }
        if (defeated) {
//            server.callDefeated();
        }
    }

    public boolean placeShip(int x, int y, Ship ship) {
        int[][] temp = cloneField(cells);

        Point offset = new Point();
        Point last = new Point(-1, -1);

        for (int i = 0; i < ship.getSize(); i++) {
            offset.set(ship.getVector().x * i, ship.getVector().y * i);
            Point actual = new Point((x + offset.x) - 1, (y + offset.y) - 1);

            if (isValidPlacement(actual, last, temp)) {
                temp[actual.x][actual.y] = OCCUPIED;
            } else {
                return false;
            }
            last.set(actual);
        }
        shipsInPlay.add(new ActualShip(x, y, ship));
        cells = temp;
        return true;
    }

    //
//            System.out.println("Offsets: x: " + offset.x + " y: " + offset.y);
//            System.out.println("targeted cell index x: " + actual.x + " y: " + actual.y);
//


//    private app.Point getShipOffset(app.Ship ship, int steps) {
//        return switch (ship.getBias()) {
//            //UP
//            case 1 -> new app.Point(0, -steps);
//            //RIGHT
//            case 2 -> new app.Point(steps, 0);
//            //DOWN
//            case 3 -> new app.Point(0, steps);
//            //LEFT
//            case 4 -> new app.Point(-steps, 0);
//            default -> null;
//        };
//    }

    private int[][] cloneField(int[][] field) {
        int[][] result = new int[field.length][];

        for (int i = 0; i < field.length; i++) {
            result[i] = Arrays.copyOf(field[i], field[i].length);
        }

        return result;
    }

    private boolean isValidPlacement(Point actual, Point last, int[][] field) {
        if (!isPointInBounds(actual)) {
            return false;
        }
        if (!isPointFree(field, actual)) {
            return false;
        }
        Point vector = new Point();
        Point current = new Point();
        for (int i = 0; i < 8; i++) {
            current.set(actual);
            vector.set(SCAN_DIRECTIONS[i][0], SCAN_DIRECTIONS[i][1]);
            current.add(vector);
            if (!current.equals(last) && isPointInBounds(current)) {
                if (!isPointFree(field, current)) {
                    return false;
                }
            }
        }
        return true;
    }

    /*
    System.out.println("current: \t" + current);
    System.out.println("actual: \t" + actual);
    System.out.println("vector: \t" + vector);
     */

    private boolean isPointInBounds(Point p) {
        return p.x >= 0 & p.y >= 0 & p.x < SIZE & p.y < SIZE;
    }

    private boolean isPointFree (int[][] field, Point p) {
        return field[p.x][p.y] == FREE;
    }

    public void printField() {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                System.out.print(cells[j][i] + "\t");
            }
            System.out.println();
        }
        System.out.println("==============================");
    }
}
