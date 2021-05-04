// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++ and C#: http://www.viva64.com
package app.shared;

public class PlayerName extends DataPackage {

    String name;

    public PlayerName(String name) {
        super(DataPackage.PLAYER_NAME);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
