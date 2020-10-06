package app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LobbyRoom extends Thread {

    private static int id;
    List<Player> players = Collections.synchronizedList(new ArrayList<>());
    private final int roomId;

    public LobbyRoom() {
        roomId = id++;
    }

    public void addPlayerToRoom(Player player) {
        players.add(player);
    }

    public int getRoomId() {
        return roomId;
    }

    @Override
    public void run() {

    }
}
