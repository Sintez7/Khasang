package app;

import thing.Thing;

import java.util.HashSet;
import java.util.Set;

public class SetDataBase implements ThingDataBase {

    Set<Thing> db = new HashSet<>();

    @Override
    public void add(Thing thing) {
        db.add(thing);
        thing.setId(thing.hashCode());
    }

    @Override
    public void removeById(int id) {
        db.remove(getThingById(id));
    }

    @Override
    public void removeThing(Thing thing) {
        db.remove(thing);
    }

    @Override
    public void showThings() {
        for (Thing thing : db) {
            System.out.println(thing.toString());
        }
    }

    @Override
    public void clear() {
        db.clear();
    }

    @Override
    public int getThingsCount() {
        return db.size();
    }

    @Override
    public boolean isPresent(Thing thing) {
        return db.contains(thing);
    }

    public Thing getThingById(int id) {
        for (Thing thing : db) {
            if (thing.getId() == id) {
                return thing;
            }
        }
        return null;
    }
}
