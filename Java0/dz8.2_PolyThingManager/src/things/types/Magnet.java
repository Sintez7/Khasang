package things.types;

import things.Thing;

public class Magnet extends Thing {

    private String from;

    public Magnet() {
        this(0);
    }

    public Magnet(int id) {
        super(id);
        name = "Magnet name";
        description = "brought from Italy";
        from = "Italy";
    }
}
