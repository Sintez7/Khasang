package things.types;

import main.ThingManager;
import things.Thing;

public class Book extends Thing {

    public Book() {
        this(null);
    }

    public Book (ThingManager manager) {
        super(manager.add(this));
        name = "Book name";
        description = "Book description";
    }

    @Override
    public String toString() {
        return "№ " + getId() + " Book " +
                "name " + name +
                " description " + description;
    }
}
