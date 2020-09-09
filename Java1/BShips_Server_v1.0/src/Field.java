import java.util.Arrays;
import java.util.Objects;

public class Field {

    private static final int FREE = 0;
    private static final int OCCUPIED = 1;
    private static final int HIT = 2;

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

    int[][] cells = new int[SIZE][SIZE];

    public Field() {
        initField(SIZE);
    }

    private void initField(int size) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells[j][i] = FREE;
            }
        }
    }

    public boolean hit(int x, int y) {
        if (cells[x - 1][y - 1] != HIT) {
            cells[x - 1][y - 1] = HIT;
            return true;
        } else {
            return false;
        }
    }

    public boolean placeShip(int x, int y, Ship ship) {
        int[][] temp = cloneField(cells);

        Point offset = new Point();
        Point last = new Point(-1, -1);

        for (int i = 0; i < ship.getSize(); i++) {
            offset.set(getShipOffset(ship, i));
            Point actual = new Point((x + offset.x) - 1, (y + offset.y) - 1);

            if (isValidPlacement(actual, last, temp)) {
                temp[actual.x][actual.y] = OCCUPIED;
            } else {
                return false;
            }
            last.set(actual.x, actual.y);
        }
        cells = temp;
        return true;
    }

    //
//            System.out.println("Offsets: x: " + offset.x + " y: " + offset.y);
//            System.out.println("targeted cell index x: " + actual.x + " y: " + actual.y);
//


    private Point getShipOffset(Ship ship, int steps) {
        return switch (ship.getBias()) {
            //UP
            case 1 -> new Point(0, -steps);
            //RIGHT
            case 2 -> new Point(steps, 0);
            //DOWN
            case 3 -> new Point(0, steps);
            //LEFT
            case 4 -> new Point(-steps, 0);
            default -> null;
        };
    }

    private int[][] cloneField(int[][] field) {
        int[][] result = new int[field.length][];

        for (int i = 0; i < field.length; i++) {
            result[i] = Arrays.copyOf(field[i], field[i].length);
        }

        return result;
    }

    private boolean isValidPlacement(Point actual, Point last, int[][] field) {
        if (!(actual.x >= 0 & actual.y >= 0 & actual.x < SIZE & actual.y < SIZE)) {
            System.out.println("failed at: placement validation 1");
            return false;
        }
        if (field[actual.x][actual.y] != FREE) {
            System.out.println("failed at: placement validation 2");
            return false;
        }

        Point vector = new Point();
        Point current = new Point();
        for (int i = 0; i < 8; i++) {
            vector.set(SCAN_DIRECTIONS[i][0], SCAN_DIRECTIONS[i][1]);
            current.add(vector);
            if (current.x != last.x & current.y != last.y) {
                if (current.x >= 0 & current.y >= 0 & current.x < SIZE & current.y < SIZE) {
                    if (field[current.x][current.y] != FREE) {
                        System.out.println("failed at: placement validation 3");
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void printField() {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                System.out.print(cells[j][i] + "\t");
            }
            System.out.println("");
        }
        System.out.println("==============================");
    }

    private class Point {
        public int x;
        public int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
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
    }
}
