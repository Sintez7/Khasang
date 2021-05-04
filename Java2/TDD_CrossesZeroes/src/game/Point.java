package game;

import java.util.Random;

public class Point {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        if (x != point.x) return false;
        return y == point.y;

    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    public static Point getRandomPoint() {
        Random random = new Random();
        return new Point(random.nextInt(Game.SIZE), random.nextInt(Game.SIZE));
    }

    @Override
    public String toString() {
        return "game.Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
