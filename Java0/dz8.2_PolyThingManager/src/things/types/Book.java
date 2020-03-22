package things.types;

import things.Thing;

public class Book extends Thing {

    public Book() {
        this(0);
    }

    public Book (int id) {
        super(id);
        name = "Book name";
        description = "Book description";
    }
}
