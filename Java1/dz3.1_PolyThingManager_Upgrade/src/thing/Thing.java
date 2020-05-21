package thing;

public class Thing {

    private int id;
    private String thingType;
    private String name = "Default name";
    private String description = "Default description";

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getThingType() {
        return thingType;
    }

    public void setThingType(String thingType) {
        this.thingType = thingType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFullDescription() {
        return thingType + " id " + getId() + "\n" +
                "Name: " + getName() + "\n" +
                "Description: " + getDescription() + "\n";
    }
}
