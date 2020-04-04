package things.types;

import things.Thing;

public class Clothes extends Thing {

    private double size;

    public Clothes() {
        this(0);
    }

    public Clothes(int id) {
        super(id);
        name = "Some clothes";
        description = "Some clothes description";
        size = (Math.random() * 10) + 10;
    }

    public double getSize() {
        return size;
    }

    @Override
    public String toString() {
        return "№ " + getId() + " Clothes " +
                "size " + size +
                " name " + name + " " +
                " description " + description;
    }
}
