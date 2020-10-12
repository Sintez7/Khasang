package app;

import app.shared.DataPackage;
import app.shared.LobbyRoomData;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * Для клиента, находящегося в комнате, информация о текущих лобби
 * является не актуальной, зато информация о состоянии текущей комнаты становится важной
 * LobbyRoom наседует Thread чтобы иметь возможность рассылать о себе информацию
 * всем игрокам что находятся в комнате, не затрагивая остальные комнаты
 */

public class LobbyRoom extends Thread {

    private static int id;
    List<Player> players = Collections.synchronizedList(new ArrayList<>());
    private final int roomId;

    private Player player1;
    private Player player2;

    public LobbyRoom() {
        roomId = id++;
    }

    public void addPlayerToRoom(Player player) {
        if (player1 == null) {
            player1 = player;
        } else if (player2 == null) {
            player2 = player;
        } else {
            players.add(player);
        }
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
            System.err.println("sending Room data");
            DataPackage data = convertToDataPackage();
            if (player1 != null) {
                try {
                    player1.sendData(data);
                } catch (SocketException e) {
                    e.printStackTrace();
                }
            }
            if (player2 != null) {
                try {
                    player2.sendData(data);
                } catch (SocketException e) {
                    e.printStackTrace();
                }
            }
            for (Player player : players) {
                try {
                    player.sendData(data);
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
        if (player1 != null) {
            result.setPlayer1Name(player1.getName());
        } else {
            result.setPlayer1Name("empty slot");
        }

        if (player2 != null) {
            result.setPlayer2Name(player2.getName());
        } else {
            result.setPlayer2Name("empty slot");
        }
        return result;
    }
}
