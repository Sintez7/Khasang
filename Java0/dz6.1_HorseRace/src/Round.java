public class Round implements Track {

    private static final double DEFAULT_LENGTH = 1000.0;
    private static final int DEFAULT_CYCLE_COUNT = 3;

    private boolean cycled = true;
    private int cycleCount;
    private double length;

    Round() {
        this(DEFAULT_CYCLE_COUNT);
    }

    public Round(int cycleCount) {
        this(cycleCount, DEFAULT_LENGTH);
    }

    public Round(int cycleCount, double length) {
        this.length = length;
        this.cycleCount = cycleCount;
    }

    @Override
    public boolean cycled() {
        return cycled;
    }

    @Override
    public int cycleCount() {
        return cycleCount;
    }

    @Override
    public double getLength() {
        return length;
    }
}
