package main;

import manager.DefaultManager;
import things.types.*;

import java.util.Random;

public class MainApp {

    ThingManager manager = new DefaultManager();

    public void start() {

        Random rand = new Random();

        System.out.println("=========== addSomeThings ===========");
        addSomeThings();
        System.out.println();
        System.out.println("=========== showThings ===========");
        manager.showThings();
        System.out.println();

        System.out.println("=========== remove 3 random items ===========");
        manager.remove(rand.nextInt(manager.getThingsCount() - 1));
        manager.remove(rand.nextInt(manager.getThingsCount() - 1));
        manager.remove(rand.nextInt(manager.getThingsCount() - 1));
        System.out.println();
        System.out.println("=========== showThings ===========");
        manager.showThings();
        System.out.println();

        System.out.println("=========== addMoreThings ===========");
        addSomeMoreThings();
        System.out.println();

        System.out.println("=========== clear ===========");
        manager.clear();
        System.out.println();
        System.out.println("=========== showThings ===========");
        manager.showThings();
        System.out.println();

        System.out.println("=========== addOnceMoreThings ===========");
        addOnceMoreSomeThings();
        System.out.println();
        System.out.println("=========== showThings ===========");
        manager.showThings();
        System.out.println();
    }

    private void addSomeThings() {
        new Book(manager);
        new Book(manager);
        new Book(manager);
        new Clothes();
        new Clothes();
        new Magnet();
        new PhotoFrame();
        new PlushToy();
        new PlushToy();
    }

    private void addSomeMoreThings() {
        new PlushToy();
        new PhotoFrame();
        new Magnet();
    }

    private void addOnceMoreSomeThings() {
        new Clothes();
        new Magnet();
        new Clothes();
        new Book();
        new PlushToy();
    }
}


/*
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

    private void addOnceMoreSomeThings() {
        manager.add(new Clothes());
        manager.add(new Magnet());
        manager.add(new Clothes());
        manager.add(new Book());
        manager.add(new PlushToy());
    }
 */