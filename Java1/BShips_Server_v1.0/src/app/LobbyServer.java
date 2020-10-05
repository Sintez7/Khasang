package app;

import app.shared.LobbiesDataPackage;
import app.shared.Lobby;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class LobbyServer extends Thread {

    private static final List<Player> players = Collections.synchronizedList(new ArrayList<>());
    private static final List<Lobby> lobbies = Collections.synchronizedList(new ArrayList<>());
    private static final List<Player> disconnectedPlayers = Collections.synchronizedList(new ArrayList<>());

    public synchronized void addPlayer(Player client) {
        players.add(client);
    }

    public LobbyServer() {
        lobbies.add(new Lobby("test1"));
    }

    @Override
    public void run() {
        while (true) {
            LobbiesDataPackage data = new LobbiesDataPackage();
            for (Lobby lobby : lobbies) {
                data.addLobbyData(lobby.convertToDataPackage());
            }

            System.err.println("sending lobbies data to " + players.size() + " players");

            for (Player player : players) {
                try {
                    player.sendData(data);
                } catch (SocketException e) {
                    disconnectedPlayers.add(player);
                }
//                player.sendData("hello");
            }
            checkList();

            try {
                Thread.sleep(5000);
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

    public void newLobby(String name) {
        lobbies.add(new Lobby(name));
    }
}
