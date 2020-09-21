import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LobbyServer extends Thread {

    private static final List<Player> players = Collections.synchronizedList(new ArrayList<>());
    private static final List<Lobby> lobbies = Collections.synchronizedList(new ArrayList<>());

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

            for (Player player : players) {
                player.sendData(data);
            }

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void newLobby(String name) {
        lobbies.add(new Lobby(name));
    }
}
