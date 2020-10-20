package app;

import app.shared.DataPackage;

import java.net.SocketException;

public class ActualPlayer implements Player{

    private static int id = 0;
    private final ClientHandler client;
    private int playerId;
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
}
