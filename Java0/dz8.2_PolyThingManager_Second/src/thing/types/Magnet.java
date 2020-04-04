package thing.types;

import thing.Thing;

public class Magnet extends Thing {

    public String uniqueStat3;

    public Magnet() {
        uniqueStat3 = "Brought from Italy";
        setThingType("Magnet");
    }

    @Override
    public String toString() {
        return getFullDescription() +
                "Unique Stat 1: " + uniqueStat3 + "\n";
    }
}
