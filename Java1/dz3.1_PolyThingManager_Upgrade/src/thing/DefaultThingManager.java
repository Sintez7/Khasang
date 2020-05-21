package thing;

import app.ThingDataBase;

public class DefaultThingManager implements ThingManager {

    ThingDataBase db;

    public DefaultThingManager(ThingDataBase db) {
        this.db = db;
    }

    @Override
    public void add(Thing thing) {
        db.add(thing);
    }

    @Override
    public void remove(int id) {
        db.removeById(id);
    }

    @Override
    public void removeThing(Thing thing) {
        db.removeThing(thing);
    }

    @Override
    public void showThings() {
        db.showThings();
    }

    @Override
    public void clear() {
        db.clear();
    }

    @Override
    public int getThingsCount() {
        return db.getThingsCount();
    }

    @Override
    public void setThingDataBase(ThingDataBase db) {
        this.db = db;
    }
}
