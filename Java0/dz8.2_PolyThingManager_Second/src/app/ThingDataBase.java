package app;

import thing.Thing;

public interface ThingDataBase {

    void add(Thing thing);
    void remove (int id);
    void showThings();
    void clear();
    int getThingsCount();
}
