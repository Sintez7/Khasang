// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++ and C#: http://www.viva64.com
package app.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LobbiesDataPackage extends DataPackage implements Serializable, LobbiesPackage {

    private List<LobbyData> list = new ArrayList<>();

    public LobbiesDataPackage() {
        super(LOBBY_PACKAGE);
    }

    public void addLobbyData(LobbyData ld) {

        if (ld != null) {
            list.add(ld);
        }
    }

    public List<LobbyData> getList() {
        return list;
    }
}
