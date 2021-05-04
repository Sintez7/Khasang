package app.shared;

public class PlayerName extends DataPackage {

    String name;

    public PlayerName(String name) {
        super(DataPackage.PLAYER_NAME);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
