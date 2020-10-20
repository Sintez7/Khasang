package app;

import app.shared.DataPackage;

import java.net.SocketException;

public interface Player {
    int getId();
    void sendData(DataPackage dataPackage) throws SocketException;

    String getName();
    void setCurrentRoom(LobbyRoom currentRoom);

    void setName(String name);
}
