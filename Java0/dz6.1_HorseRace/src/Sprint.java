public class Sprint implements Track {

    private static final double DEFAULT_LENGTH = 1000.0;

    private boolean cycled = false;
    private int cycleCount = 0;
    private double length;

    Sprint() {
        this(DEFAULT_LENGTH);
    }

    Sprint(double length) {
        this.length = length;
    }

    @Override
    public boolean cycled() {
        return false;
    }

    @Override
    public int cycleCount() {
        return 0;
    }

    @Override
    public double getLength() {
        return length;
    }
}
