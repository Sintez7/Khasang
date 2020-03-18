public class TimeAttack implements Track {

    private static final double DEFAULT_LENGTH = 1000.0;

    private boolean cycled = false;
    private int cycleCount = 0;
    private double length;

    TimeAttack() {
        this(DEFAULT_LENGTH);
    }

    public TimeAttack(double length) {
        this.length = length;
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
