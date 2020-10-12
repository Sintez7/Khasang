package app;

import app.shared.LobbyData;
import app.shared.LobbyRoomData;

import java.util.List;

public interface Controllable {
    void addLobbies(List<LobbyData> dPackage);
    void receiveRoomInfo(LobbyRoomData data);
}
