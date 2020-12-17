package app;

import app.shared.DataPackage;

import java.net.SocketException;
import java.util.Objects;

public class ActualPlayer implements Player{

    private static int id = 0;
    private final ClientHandler client;
    private final int playerId;
    private String name = "testName";

    public ActualPlayer(ClientHandler client) {
        this.client = client;
        playerId = id++;
    }

    @Override
    public int getId() {
        return playerId;
    }

    @Override
    public void sendData(DataPackage dataPackage) throws SocketException {
        client.sendData(dataPackage);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setCurrentRoom(LobbyRoom currentRoom) {
        client.setCurrentRoom(currentRoom);
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setCurrentGameServer(GameServer gameServer) {
        client.setCurrentGameServer(gameServer);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ActualPlayer that = (ActualPlayer) o;
        return playerId == that.playerId &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerId, name);
    }
}
