package things.types;

import things.Thing;

public class PlushToy extends Thing {

    public PlushToy() {
        this(0);
    }

    public PlushToy(int id) {
        super(id);
        name = "Plush Toy name";
        description = "Plush Toy description";
    }

    @Override
    public String toString() {
        return "№ " + getId() + " Plush Toy " +
                "name " + name +
                " description " + description;
    }
}
