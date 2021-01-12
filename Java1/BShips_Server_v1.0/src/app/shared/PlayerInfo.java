// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++ and C#: http://www.viva64.com
package app.shared;

public class PlayerInfo extends DataPackage {

    private int playerInfo;
    private String playerName;
    private String opponentName;

    public PlayerInfo(int playerInfo, String playerName, String opponentName) {
        super(PLAYER_INFO);
        this.playerInfo = playerInfo;
        this.playerName = playerName;
        this.opponentName = opponentName;
    }

    public int getPlayerInfo() {
        return playerInfo;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getOpponentName() {
        return opponentName;
    }
}
