// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++ and C#: http://www.viva64.com
package app.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LobbyRoomData extends DataPackage implements Serializable  {

    String player1Name;
    String player2Name;
    String roomName;
    List<String> playersNames = new ArrayList<>();
    int roomId;

    public LobbyRoomData() {
        this(DataPackage.ROOM);
    }

    public LobbyRoomData(int id) {
        super(DataPackage.ROOM);
    }

    public void addPlayerToList (String name) {
        playersNames.add(name);
    }

    public List<String> getPlayersNames() {
        return playersNames;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getPlayer1Name() {
        return player1Name;
    }

    public void setPlayer1Name(String player1Name) {
        this.player1Name = player1Name;
    }

    public String getPlayer2Name() {
        return player2Name;
    }

    public void setPlayer2Name(String player2Name) {
        this.player2Name = player2Name;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
}
