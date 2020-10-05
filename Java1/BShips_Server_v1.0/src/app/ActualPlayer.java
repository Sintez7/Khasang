package app;

import app.shared.DataPackage;

import java.net.SocketException;

public class ActualPlayer implements Player{

    private final ClientHandler client;

    public ActualPlayer(ClientHandler client) {
        this.client = client;
    }

    @Override
    public void sendData(DataPackage dataPackage) throws SocketException {
        client.sendData(dataPackage);
    }
}
