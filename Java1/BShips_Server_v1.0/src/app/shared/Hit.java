package app.shared;

public class Hit extends DataPackage {
    private final int x;
    private final int y;

    public Hit(int x, int y) {
        super(DataPackage.HIT);
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
