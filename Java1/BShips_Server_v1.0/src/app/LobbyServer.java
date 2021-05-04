// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++ and C#: http://www.viva64.com
package app;

import app.shared.LobbiesDataPackage;
import app.shared.Lobby;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/*
 * Класс занимается рассылкой информации о лобби всем подключенным клиентам
 * Т.к. число как игроков, так и лобби постоянно будет меняться,
 * LobbyServer в вечном цикле раздаёт всем клиентам информацию о текущих лобби
 */

public class LobbyServer extends Thread {

    private static final List<Player> players = Collections.synchronizedList(new ArrayList<>());
    private static final List<Lobby> lobbies = Collections.synchronizedList(new ArrayList<>());
    private static final List<Player> disconnectedPlayers = Collections.synchronizedList(new ArrayList<>());
    private final LobbyRoomHandler roomHandler = new LobbyRoomHandler(this);

    public synchronized void addPlayer(Player client) {
        players.add(client);
    }

    public LobbyServer() {
        lobbies.add(new Lobby("test1"));
    }

    @Override
    public void run() {
        while (true) {
            // Генерируем пакет о текущем состоянии
            LobbiesDataPackage data = new LobbiesDataPackage();
            for (Lobby lobby : lobbies) {
                data.addLobbyData(lobby.convertToDataPackage());
            }

//            System.err.println("sending lobbies data to " + players.size() + " players");

            // Отправляем пакет всем игрокам
            for (Player player : players) {
                try {
                    player.sendData(data);
                } catch (SocketException e) {
                    // если отправить не смог - считаем клиент отвалившимся
                    disconnectedPlayers.add(player);
                }
            }
            // Убираем отвалившиеся клиенты из списка активных
            checkList();

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void checkList() {
        Iterator<Player> i = players.listIterator();
        while (i.hasNext()) {
            Player temp = i.next();
            for (Player dp : disconnectedPlayers) {
                if (dp.equals(temp)) i.remove();
            }
        }
    }

    public Lobby newLobby(String name) {
        Lobby temp = new Lobby(name);
        lobbies.add(temp);
        return temp;
    }

    public void movePlayerToLobbyRoom(Player player, int lobbyId) {
        roomHandler.acceptPlayer(player, lobbyId, this);
        players.remove(player);
    }

    public void removeLobby(int roomId) {
        lobbies.removeIf(temp -> temp.getId() == roomId);
    }
}
