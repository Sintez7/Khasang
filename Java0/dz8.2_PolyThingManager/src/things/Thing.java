package things;

public abstract class Thing {

    public String name = "";
    public String description = "";
    private int id;

    public Thing (int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
