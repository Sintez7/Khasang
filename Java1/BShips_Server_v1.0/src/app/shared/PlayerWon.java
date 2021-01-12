// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++ and C#: http://www.viva64.com
package app.shared;

public class PlayerWon extends DataPackage {

    private int playerWon;

    public PlayerWon(int playerWon) {
        super(PLAYER_WON);
        this.playerWon = playerWon;
    }

    public int getPlayerWon() {
        return playerWon;
    }
}
