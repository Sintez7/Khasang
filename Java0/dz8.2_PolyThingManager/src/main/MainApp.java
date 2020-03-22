package main;

import things.types.*;

import java.util.Random;

public class MainApp {

    ThingManager manager = Managers.DEFAULT_MANAGER.getInstance();

    public void start() {

        Random rand = new Random();

        addSomeThings();
        manager.showThings();

        manager.remove(rand.nextInt(manager.getThingsCount() - 1));
        manager.remove(rand.nextInt(manager.getThingsCount() - 1));
        manager.remove(rand.nextInt(manager.getThingsCount() - 1));
        manager.showThings();

        addSomeMoreThings();

        manager.clear();
        manager.showThings();

        addOneMoreTimeSomeThings();
        manager.showThings();
    }

    private void addSomeThings() {
        manager.add(new Book());
        manager.add(new Book());
        manager.add(new Book());
        manager.add(new Clothes());
        manager.add(new Clothes());
        manager.add(new Magnet());
        manager.add(new PhotoFrame());
        manager.add(new PlushToy());
        manager.add(new PlushToy());
    }

    private void addSomeMoreThings() {
        manager.add(new PlushToy());
        manager.add(new PhotoFrame());
        manager.add(new Magnet());
    }

    private void addOneMoreTimeSomeThings() {
        manager.add(new Clothes());
        manager.add(new Magnet());
        manager.add(new Clothes());
        manager.add(new Book());
        manager.add(new PlushToy());
    }
}
