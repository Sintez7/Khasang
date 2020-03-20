package tracks.types;

import horses.Horse;
import tracks.Track;

import java.util.Comparator;

public class Round implements Track {

    private static final double DEFAULT_LENGTH = 1000.0;
    private static final int DEFAULT_CYCLE_COUNT = 3;

    private boolean cycled = true;
    private int cycleCount = 0;
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

    @Override
    public Comparator<Horse> getWinComparator() {

        return new Comparator<>() {
            @Override
            public int compare(Horse o1, Horse o2) {

                if (o1.getTime() > o2.getTime()) {
                    return 1;
                } else {
                    if (o1.getTime() < o2.getTime()) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            }
        };
    }
}
