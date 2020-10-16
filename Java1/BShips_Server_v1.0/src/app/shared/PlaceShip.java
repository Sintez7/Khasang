package app.shared;

public class PlaceShip extends DataPackage {
    private final int x;
    private final int y;
    private int size;
    private int bias;

    public PlaceShip(int x, int y, int size, int bias) {
        super(PLACE_SHIP);
        this.x = x;
        this.y = y;
        this.size = size;
        this.bias = bias;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSize() {
        return size;
    }

    public int getBias() {
        return bias;
    }
}
