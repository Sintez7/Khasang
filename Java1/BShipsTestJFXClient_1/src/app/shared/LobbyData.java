package app.shared;

import java.io.Serializable;

public class LobbyData implements Serializable {

    private String name;
    private int id;

    public LobbyData() {
        this(null);
    }

    public LobbyData(Lobby lobby) {
        if (lobby != null) {
            name = lobby.getName();
            id = lobby.getId();
        }
    }

    public String lobbyName() {
        return name;
    }

    public int lobbyId() {
        return id;
    }
}

