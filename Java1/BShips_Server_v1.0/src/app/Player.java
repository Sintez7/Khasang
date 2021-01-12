// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++ and C#: http://www.viva64.com
package app;

import app.shared.DataPackage;

import java.net.SocketException;

public interface Player {
    int getId();
    void sendData(DataPackage dataPackage) throws SocketException;

    String getName();
    void setCurrentRoom(LobbyRoom currentRoom);

    void setName(String name);

    void setCurrentGameServer(GameServer gameServer);
}
