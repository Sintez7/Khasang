// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++ and C#: http://www.viva64.com
package app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LobbyRoomHandler {

    List<LobbyRoom> rooms = Collections.synchronizedList(new ArrayList<>());
    LobbyServer ls;

    public LobbyRoomHandler(LobbyServer lobbyServer) {
        ls = lobbyServer;
    }

    public void acceptPlayer(Player player, int lobbyId, LobbyServer lobbyServer) {
        boolean found = false;

        for (LobbyRoom room : rooms) {
            if (room.getRoomId() == lobbyId) {
                room.addPlayerToRoom(player);
                found = true;
                break;
            }
        }

        if (!found) {
            LobbyRoom temp = new LobbyRoom(lobbyServer);
            temp.addPlayerToRoom(player);
            rooms.add(temp);
            temp.start();
        }
    }
}
