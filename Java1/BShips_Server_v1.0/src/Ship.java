public class Ship {

    private final int size;

    public static final int UP = 1;
    public static final int RIGHT = 2;
    public static final int DOWN = 3;
    public static final int LEFT = 4;

    private int bias;

    public Ship(int size, int bias) {
        this.size = size;
        this.bias = bias;
    }

    public int getBias() {
        return bias;
    }

    public int getSize() {
        return size;
    }
}
