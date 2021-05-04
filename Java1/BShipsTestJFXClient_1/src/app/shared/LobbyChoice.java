package app.shared;

public class LobbyChoice extends DataPackage {

    public int lobbyId;

    public LobbyChoice(int lobbyId) {
        super(DataPackage.LOBBY_CHOICE);
        this.lobbyId = lobbyId;
    }
}
