package app.shared;

import java.io.Serializable;

public abstract class DataPackage implements Serializable {
    private static final long serialVersionUID = 101015;

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

    private final int id;

    public DataPackage(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
