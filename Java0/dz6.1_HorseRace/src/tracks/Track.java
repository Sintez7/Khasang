package tracks;

import horses.Horse;

import java.util.Comparator;

public interface Track {
    boolean cycled();

    int cycleCount();

    double getLength();

    Comparator<Horse> getWinComparator();
}
