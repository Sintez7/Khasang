package app;

import app.shared.DataPackage;

import java.net.SocketException;

public class ActualPlayer implements Player{

    private static int id = 0;
    private final ClientHandler client;
    private int playerId;

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
}
