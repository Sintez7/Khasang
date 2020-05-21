package thing;

import app.ThingDataBase;

public interface ThingManager {

    void add(Thing thing);
    void remove(int id);
    void removeThing(Thing thing);
    void showThings();
    void clear();
    int getThingsCount();
    void setThingDataBase(ThingDataBase db);
}
