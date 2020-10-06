package app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LobbyRoomHandler {

    List<LobbyRoom> rooms = Collections.synchronizedList(new ArrayList<>());

    public void createLobbyRoom() {
        rooms.add(new LobbyRoom());
    }

    public void acceptPlayer (Player player, int lobbyId) {
        for (LobbyRoom room : rooms) {
            if (room.getRoomId() == lobbyId) {
                room.addPlayerToRoom(player);
                break;
            }
        }
    }
}
