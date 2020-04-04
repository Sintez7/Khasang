package app;

import thing.Thing;

import java.util.ArrayList;
import java.util.List;

public class DefaultThingDataBase implements ThingDataBase {
    List<Thing> list = new ArrayList<>();
    int id = 0;

    @Override
    public void add(Thing thing) {
        list.add(thing);
        thing.setId(id++);
    }

    @Override
    public void remove(int id) {
        list.remove(getThingById(id));
    }

    @Override
    public void showThings() {
        for (Thing thing : list) {
            System.out.println(thing.toString());
        }
    }

    @Override
    public void clear() {
        list.clear();
        id = 0;
    }

    public Thing getThingById(int id) {
        for (Thing thing : list) {
            if (thing.getId() == id) {
                return thing;
            }
        }
        System.out.println("No thing with such id found!");
        return null;
    }

    @Override
    public int getThingsCount() {
        return list.size();
    }
}
