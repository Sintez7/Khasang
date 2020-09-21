public class LobbyData {

    private String name;

    public LobbyData() {
        this(null);
    }

    public LobbyData(Lobby lobby) {
        if (lobby != null) {
            name = lobby.getName();
        }
    }

    public String lobbyName() {
        return name;
    }
}
