// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++ and C#: http://www.viva64.com
package app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Field {

    protected static final int FREE = 0;
    protected static final int OCCUPIED = 1;
    protected static final int HIT = 2;
    protected static final int OCCUPIED_HIT = 3;

    private static final int SIZE = 10;

    private static final int[][] SCAN_DIRECTIONS =
            {{0, -1},
                    {1, -1},
                    {1, 0},
                    {1, 1},
                    {0, 1},
                    {-1, 1},
                    {-1, 0},
                    {-1, -1}};

    private final List<ActualShip> shipsInPlay = new ArrayList<>();
    int[][] cells = new int[SIZE][SIZE];
    private boolean sunk = false;

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

    public HitResult hit(int x, int y) {
        System.err.println("hit in Field invoked");
        System.err.println("current Thread: " + Thread.currentThread());
        System.err.println("hit point x" + x + " y" + y);
        sunk = false;
        if (cells[x][y] != HIT) {
            if (hitPoint(x, y)) {
//                checkLose();
                if (sunk) {
                    System.err.println("result SUNK_SHIP");
                    return HitResult.SUNK_SHIP;
                } else {
                    System.err.println("result HIT_SHIP");
                    return  HitResult.HIT_SHIP;
                }
            } else {
//                server.miss();
                System.err.println("result MISS");
                return HitResult.MISS;
            }
        } else {
//            server.pointHit();
            System.err.println("result ALREADY_HIT");
            return HitResult.POINT_ALREADY_HIT;
        }
    }

    private boolean hitPoint(int x, int y) {
        if (hitShip(x, y)) {
            cells[x][y] = OCCUPIED_HIT;
            return true;
        } else {
            cells[x][y] = HIT;
            return false;
        }
    }

    private boolean hitShip(int x, int y) {
        for (ActualShip actualShip : shipsInPlay) {
            if (actualShip.hit(x, y)) {
                return true;
            }
        }
        cells[x][y] = HIT;
        return false;
    }

    public boolean checkLose() {
        for (ActualShip actualShip : shipsInPlay) {
            System.err.println(actualShip.toString());
            if (actualShip.isAlive()) {
                return false;
            }
        }
        return true;
    }

    public boolean placeShip(int x, int y, int shipSize, int shipBias) {
        System.err.println("placeShip in Field invoked");
        System.err.println("ship x: " + x + ", ship y: " + y + ", shipSize: " + shipSize + ", shipBias: " + shipBias);
        Ship tempShip = new Ship(shipSize, shipBias);
        int[][] temp = cloneField(cells);

        Point offset = new Point();
        Point last = new Point(-1, -1);

        for (int i = 0; i < tempShip.getSize(); i++) {
            offset.set(tempShip.getVector().x * i, tempShip.getVector().y * i);
            Point actual = new Point((x + offset.x), (y + offset.y));

            if (isValidPlacement(actual, last, temp)) {
                temp[actual.x][actual.y] = OCCUPIED;
            } else {
                System.err.println("placement not valid");
                return false;
            }
            last.set(actual);
        }
        shipsInPlay.add(new ActualShip(x, y, tempShip));
        cells = cloneField(temp);
        return true;
    }

    private boolean isValidPlacement(Point actual, Point last, int[][] field) {
        if (!isPointInBounds(actual)) {
            System.err.println("point not in bounds");
            return false;
        }
        if (!isPointFree(field, actual)) {
            System.err.println("point not free");
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
                    System.err.println("ship collision detected");
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

    private int[][] cloneField(int[][] field) {
        int[][] result = new int[field.length][];

        for (int i = 0; i < field.length; i++) {
            result[i] = Arrays.copyOf(field[i], field[i].length);
        }

        return result;
    }

    public void printField() {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                System.err.print(cells[j][i] + "\t");
            }
            System.err.println();
        }
        System.err.println("==============================");
    }

    public void checkSunkShips(FakeField player1OpponentField) {
        for (ActualShip actualShip : shipsInPlay) {
            if (!actualShip.isCircled() && !actualShip.isAlive()) {
                circleSunkShip(actualShip, player1OpponentField);
                actualShip.setCircled();
                sunk = true;
            }
        }
    }

    private void circleSunkShip(ActualShip ship, FakeField player1OpponentField) {
        Point[] points = ship.getShipPoint();
        Point vector = new Point();
        Point current = new Point();
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < 8; j++) {
                current.set(points[i].x, points[i].y);
                vector.set(SCAN_DIRECTIONS[j][0], SCAN_DIRECTIONS[j][1]);
                current.add(vector);
                if (isPointInBounds(current)) {
                    int temp = setHit(current);
                    player1OpponentField.setPoint(current.x, current.y, temp);
                }
            }

        }
    }

    private int setHit(Point p) {
        if (cells[p.x][p.y] == FREE) {
            cells[p.x][p.y] = HIT;
        } else if (cells[p.x][p.y] == OCCUPIED) {
            cells[p.x][p.y] = OCCUPIED_HIT;
            return OCCUPIED_HIT;
        } else if (cells[p.x][p.y] == OCCUPIED_HIT) {
            return OCCUPIED_HIT;
        }
        return HIT;
    }

    enum HitResult {
        HIT_SHIP,
        MISS,
        POINT_ALREADY_HIT,
        SUNK_SHIP
    }
}
