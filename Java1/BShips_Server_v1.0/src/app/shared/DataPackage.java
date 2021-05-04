// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++ and C#: http://www.viva64.com
package app.shared;

import java.io.Serializable;

public abstract class DataPackage implements Serializable {
//    private static final long serialVersionUID = 101015;

    public static final int LOBBY_PACKAGE = 1;
    public static final int GAMESERVER_PACKAGE = 2;
    public static final int CHAT_MESSAGE_PACKAGE = 3;
    public static final int PLAYER_NAME = 4;
    public static final int CREATE_LOBBY = 5;
    public static final int RETURN_TO_LOBBY = 6;
    public static final int LEAVE_ROOM = 7;
    public static final int ENTER_ROOM = 8;
    public static final int LOBBY_CHOICE = 11;
    public static final int ROOM = 12;
    public static final int GAME_START = 13;
    public static final int PLACE_SHIP = 14;
    public static final int PLAYER_MOVE = 15;
    public static final int PLACE_SHIP_RESPONSE = 16;
    public static final int HIT = 17;
    public static final int HIT_RESPONSE = 18;
    public static final int TURN_UPDATE = 19;
    public static final int READY_TO_GAME_START = 20;
    public static final int REMATCH_DECISION = 21;
    public static final int PLAYER_INFO = 22;
    public static final int REMATCH_OFFER = 23;
    public static final int PLAYER_WON = 24;
    public static final int REMATCH_SIGNAL = 25;
    public static final int BATTLE_START = 26;
    public static final int REMATCH_DENIED = 27;
    public static final int PLAY_VS_AI = 28;

    private final int id;

    public DataPackage(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
