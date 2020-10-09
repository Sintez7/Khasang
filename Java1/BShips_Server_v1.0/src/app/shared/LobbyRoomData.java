package app.shared;

import java.util.ArrayList;
import java.util.List;

public class LobbyRoomData extends DataPackage {

    List<String> playersNames = new ArrayList<>();
    int roomId;

    public LobbyRoomData() {
        this(DataPackage.LOBBY_ROOM_PACKAGE);
    }

    public LobbyRoomData(int id) {
        super(DataPackage.LOBBY_ROOM_PACKAGE);
    }

    public void addPlayerToList (String name) {
        playersNames.add(name);
    }

    public List<String> getPlayersNames() {
        return playersNames;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
}
