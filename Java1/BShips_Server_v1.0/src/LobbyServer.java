import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LobbyServer extends Thread {

    private static final List<Player> players = Collections.synchronizedList(new ArrayList<>());
    private static final List<Lobby> lobbies = Collections.synchronizedList(new ArrayList<>());

    public synchronized void addPlayer(Player client) {
        players.add(client);
    }

    @Override
    public void run() {
        while (true) {
            for (Player player : players) {
                for (Lobby lobby : lobbies) {
                    player.sendData(lobby.convertToDataPackage());
                }
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
