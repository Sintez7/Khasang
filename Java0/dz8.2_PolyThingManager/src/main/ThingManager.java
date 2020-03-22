package main;

import things.Thing;

public abstract class ThingManager {

    public abstract int add(Thing thing);
    public abstract void remove(int id);
    public abstract void showThings();
    public abstract void clear();
    public abstract Thing getThingById(int id);
    public abstract int getThingsCount();
}
