package thing.types;

import thing.Thing;

public class Book extends Thing {

    public String uniqueStat1;

    public Book() {
        uniqueStat1 = "Author: Lev Tolstoy";
        setThingType("Book");
    }

    @Override
    public String toString() {
        return getFullDescription() +
                "Unique Stat 1: " + uniqueStat1 + "\n";
    }
}
