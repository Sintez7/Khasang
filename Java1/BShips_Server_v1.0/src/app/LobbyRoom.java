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
    List<Player> spectators = Collections.synchronizedList(new ArrayList<>());
    private final int roomId;

    private Player player1 = null;
    private Player player2 = null;

    public LobbyRoom() {
        roomId = id++;
        setName("LobbyRoom " + roomId);
    }

    public void addPlayerToRoom(Player player) {
        if (player1 == null) {
            player1 = player;
        } else if (player2 == null) {
            player2 = player;
        } else {
            spectators.add(player);
        }
        player.setCurrentRoom(this);
    }

    public void removePlayerFromRoom(Player player) {
        spectators.remove(player);
    }

    public int getRoomId() {
        return roomId;
    }

    @Override
    public void run() {
        while (player1 != null || player2 != null || spectators.size() > 0) {
            System.err.println("sending Room data");
            DataPackage data = convertToDataPackage();
            if (player1 != null) {
                try {
                    player1.sendData(data);
                } catch (SocketException e) {
                    System.err.println("LobbyRoom.run.player1 SocketException");
                    player1 = null;
                }
            }
            if (player2 != null) {
                try {
                    player2.sendData(data);
                } catch (SocketException e) {
                    System.err.println("LobbyRoom.run.player2 SocketException");
                    player2 = null;
                }
            }
            for (Player player : spectators) {
                try {
                    player.sendData(data);
                } catch (SocketException e) {
                    System.err.println("LobbyRoom.run.spectator SocketException");
                }
            }
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private DataPackage convertToDataPackage() {
        LobbyRoomData result = new LobbyRoomData();
        for (Player player : spectators) {
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

        result.setRoomName("Room " + roomId);
        return result;
    }

    public GameServer startGame() {
        if (player1 != null && player2 != null) {
            //TODO startGame
            clearRoom();
            return new GameServer(player1, player2, spectators).startGame();
        } else {
            return null;
        }
    }

    private void clearRoom() {
        player1 = null;
        player2 = null;
        spectators.clear();
    }
}
