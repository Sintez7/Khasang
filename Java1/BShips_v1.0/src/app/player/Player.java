package app.player;

public abstract class Player {

    private String name;

    public Player() {
        this("");
    }

    public Player(String name) {
        this.name = name;
    }

    protected void setName(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
