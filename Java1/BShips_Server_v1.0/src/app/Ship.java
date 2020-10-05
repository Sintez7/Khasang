package app;

public class Ship {

    private final int size;

    public static final int UP = 1;
    public static final int RIGHT = 2;
    public static final int DOWN = 3;
    public static final int LEFT = 4;

    private final int bias;
    private final Point vector = new Point();

    public Ship(int size, int bias) {
        this.size = size;
        this.bias = bias;
        switch (bias) {
            //UP
            case 1 -> vector.set(0, -1);
            //RIGHT
            case 2 -> vector.set(1, 0);
            //DOWN
            case 3 -> vector.set(0, 1);
            //LEFT
            case 4 -> vector.set(-1, 0);
        }
    }

    public int getBias() {
        return bias;
    }

    public int getSize() {
        return size;
    }

    public Point getVector() {
        return vector;
    }
}