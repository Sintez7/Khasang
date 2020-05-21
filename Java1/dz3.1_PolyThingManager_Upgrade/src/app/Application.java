package app;

import thing.DefaultThingManager;
import thing.Thing;
import thing.ThingManager;
import thing.types.*;

import java.util.Random;

public class Application implements AppInterface {

//    ThingDataBase db = new DefaultThingDataBase();
    ThingDataBase db = new SetDataBase();
    ThingManager manager = new DefaultThingManager(db);

    @Override
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

//===================================================================================================
        System.out.println("=========== NEW ===========");
        System.out.println("=========== add new thing ===========");

        Thing thing = new Book();
        db.add(thing);

        System.out.println("=========== showThings ===========");
        manager.showThings();
        System.out.println();

        System.out.println("=========== is thing present in dataBase ===========");
        System.out.println(thing + "\n is present in db: " + db.isPresent(thing));
        System.out.println();

        System.out.println("=========== removeThing ===========");
        manager.removeThing(thing);
        System.out.println();

        System.out.println("=========== is thing present in dataBase ===========");
        System.out.println(thing + "\n is present in db: " + db.isPresent(thing));
        System.out.println();
    }

    private void addSomeThings() {
        manager.add(new Book());
        manager.add(new Book());
        manager.add(new Book());
        manager.add(new Clothes());
        manager.add(new Clothes());
        manager.add(new Magnet());
    }

    private void addSomeMoreThings() {
        manager.add(new Magnet());
    }

    private void addOnceMoreSomeThings() {
        manager.add(new Clothes());
        manager.add(new Magnet());
        manager.add(new Clothes());
        manager.add(new Book());
    }
}
