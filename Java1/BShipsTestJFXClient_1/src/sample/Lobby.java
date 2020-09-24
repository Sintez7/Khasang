package sample;

public class Lobby {

    private static int idCounter = 0;

    private boolean started = false;
    private String name;
    private int id;

    public Lobby() {
        this("");
    }

    public Lobby(String name) {
        if (!name.equals("")) {
            this.name = name;
        } else {
            this.name = "Unnamed app.Lobby";
        }

        id = idCounter++;
    }

    public Lobby(LobbyData data) {
        this.name = data.lobbyName();
        this.id = data.lobbyId();
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public LobbyData convertToDataPackage() {
        return new LobbyData(this);
    }
}
