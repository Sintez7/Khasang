// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++ and C#: http://www.viva64.com
package app;

import app.botPlayer.BotPlayer;
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

    private volatile Player player1 = null;
    private volatile Player player2 = null;

    private final LobbyServer lobbyServer;

    public LobbyRoom(LobbyServer lobbyServer) {
        roomId = id++;
        setName("LobbyRoom " + roomId);
        this.lobbyServer = lobbyServer;
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

        if (player1 != null) {
            try {
                player1.sendData(convertToDataPackage());
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }

        if (player2 != null) {
            try {
                player2.sendData(convertToDataPackage());
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }
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
        lobbyServer.removeLobby(roomId);
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
            GameServer s = new GameServer(player1, player2, spectators);
            clearRoom();
            return s.startGame();
        } else {
            return null;
        }
    }

    public void returnPlayerToLobbyServer(Player player) {
        if (player.equals(player1)) {
            player1 = null;
        } else if (player.equals(player2)) {
            player2 = null;
        } else {
            spectators.remove(player);
        }
        player.setCurrentRoom(null);
    }

    private void clearRoom() {
        player1 = null;
        player2 = null;
        spectators.clear();
    }

    public void handlePvA() {
        if (player1 == null) {
            player1 = new BotPlayer();
        }

        if (player2 == null) {
            player2 = new BotPlayer();
        }
    }
}
