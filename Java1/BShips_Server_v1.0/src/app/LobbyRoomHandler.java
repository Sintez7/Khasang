package app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LobbyRoomHandler {

    List<LobbyRoom> rooms = Collections.synchronizedList(new ArrayList<>());

    public LobbyRoom createLobbyRoom() {
        LobbyRoom temp = new LobbyRoom();
        rooms.add(temp);
        temp.start();
        return temp;
    }

    public void acceptPlayer (Player player, int lobbyId) {
        boolean found = false;

        for (LobbyRoom room : rooms) {
            if (room.getRoomId() == lobbyId) {
                room.addPlayerToRoom(player);
                found = true;
                break;
            }
        }

        if (!found) {
            LobbyRoom temp = new LobbyRoom();
            temp.addPlayerToRoom(player);
            rooms.add(temp);
            temp.start();
        }
    }
}
