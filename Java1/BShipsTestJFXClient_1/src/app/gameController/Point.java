package app.gameController;

import java.util.Objects;

public class Point {
    public int x;
    public int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point p) {
        x = p.x;
        y = p.y;
    }

    public Point() {
        this(0, 0);
    }

    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void set(Point point) {
        x = point.x;
        y = point.y;
    }
    public void add(Point point) {
        x += point.x;
        y += point.y;
    }

    public Point plus(Point p) {
        return new Point (x + p.x, y + p.y);
    }

    public Point inverse() {
        return new Point (x * -1, y * -1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Point point = (Point) o;
        return x == point.x &&
                y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "app.Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}