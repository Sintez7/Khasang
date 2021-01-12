// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++ and C#: http://www.viva64.com
package app;

import app.shared.LobbyData;
import app.shared.LobbyRoomData;

import java.util.List;

public interface Controllable {
    void addLobbies(List<LobbyData> dPackage);
    void receiveRoomInfo(LobbyRoomData data);
}
