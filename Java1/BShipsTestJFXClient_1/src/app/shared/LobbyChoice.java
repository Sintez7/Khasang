package app.shared;

public class LobbyChoice extends DataPackage {

    public int lobbyId;

    public LobbyChoice(int lobbyId) {
        super(11);
        this.lobbyId = lobbyId;
    }
}
