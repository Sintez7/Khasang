package tracks;

import horses.Horse;

import java.util.Comparator;

public class TimeAttack implements Track {

    private static final double DEFAULT_LENGTH = 1000.0;

    private boolean cycled = false;
    private int cycleCount = 0;
    private double length;

    TimeAttack() {
        this(DEFAULT_LENGTH);
    }

    TimeAttack(double length) {
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
