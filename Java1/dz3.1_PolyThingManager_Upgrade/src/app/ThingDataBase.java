package app;

import thing.Thing;

public interface ThingDataBase {

    void add(Thing thing);
    void removeById(int id);
    void removeThing(Thing thing);
    void showThings();
    void clear();
    int getThingsCount();
    boolean isPresent(Thing thing);
}
