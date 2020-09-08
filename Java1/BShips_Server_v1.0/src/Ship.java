public class Ship {

    private final int size;

    private Bias bias;

    public Ship(int size, Bias bias) {
        this.size = size;
        this.bias = bias;
    }

    public Bias getBias() {
        return bias;
    }

    public int getSize() {
        return size;
    }

    public enum Bias {
        UP,
        DOWN,
        RIGHT,
        LEFT
    }
}
