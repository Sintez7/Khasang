package app;

import app.shared.DataPackage;
import app.shared.LobbyRoomData;

import java.net.SocketException;
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

    public void removePlayerFromRoom(Player player) {
        players.remove(player);
    }

    public int getRoomId() {
        return roomId;
    }

    @Override
    public void run() {
        while (true) {
            for (Player player : players) {
                try {
                    player.sendData(convertToDataPackage());
                } catch (SocketException e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private DataPackage convertToDataPackage() {
        LobbyRoomData result = new LobbyRoomData();
        for (Player player : players) {
            result.addPlayerToList(player.getName());
        }
        result.setRoomId(roomId);
        return result;
    }
}
