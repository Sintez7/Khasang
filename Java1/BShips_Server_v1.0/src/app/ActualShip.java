package app;

import java.util.HashMap;
import java.util.Map;

public class ActualShip {

    public static final int ALIVE = 1;
    public static final int DEAD = 0;

    private final Map<Point, Integer> occupiedPoints = new HashMap<>();
    private int size;

    private Point bias;
    private boolean circled = false;

    private int initX;
    private int initY;

    public ActualShip(int x, int y, Ship ship) {
        initX = x;
        initY = y;
        bias = ship.getVector();
        size = ship.getSize();
        for (int i = 0; i < ship.getSize(); i++) {
            Point temp = new Point();
            temp.set(x + (bias.x * i), y + (bias.y * i));
            occupiedPoints.put(temp, ALIVE);
        }
    }

    public boolean isAlive() {
        boolean result = false;
        for (Integer value : occupiedPoints.values()) {
            if (value == ALIVE) {
                result = true;
                break;
            }
        }
        return result;
    }

    public boolean hit(int x, int y) {
        Point target = new Point (x, y);
        for (Point point : occupiedPoints.keySet()) {
            if (point.equals(target)) {
                occupiedPoints.put(point, DEAD);
                return true;
            }
        }
        return false;
    }

    public Point[] getShipPoint() {
        Point[] result = new Point[size];
        int counter = 0;
        for (Point point : occupiedPoints.keySet()) {
            result[counter] = point;
            counter++;
        }
        return result;
    }

    public Point getBias() {
        return bias;
    }

    public void setCircled() {
        circled = true;
    }

    public boolean isCircled() {
        return circled;
    }

    @Override
    public String toString() {
        return "ActualShip, size " + size + ", x " + initX + ", y " + initY + ", alive? " + isAlive();
    }
}
