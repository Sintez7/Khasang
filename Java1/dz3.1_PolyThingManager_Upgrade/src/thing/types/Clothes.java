package thing.types;

import thing.Thing;

public class Clothes extends Thing {

    public String uniqueStat2;

    public Clothes() {
        uniqueStat2 = "Size : XL";
        setThingType("Clothes");
        setName("TShirt");
        setDescription("standard TShirt");
    }

    @Override
    public String toString() {
        return getFullDescription() +
                "Unique Stat 1: " + uniqueStat2 + "\n";
    }
}
