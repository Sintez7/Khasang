// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++ and C#: http://www.viva64.com
package app.shared;

public class LobbyChoice extends DataPackage {

    public int lobbyId;

    public LobbyChoice(int lobbyId) {
        super(DataPackage.LOBBY_CHOICE);
        this.lobbyId = lobbyId;
    }
}
