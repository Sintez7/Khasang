package app.shared;

import java.io.Serializable;

public abstract class DataPackage implements Serializable {
    private static final long serialVersionUID = 101003;

    public static final int LOBBY_PACKAGE = 1;
    public static final int GAMESERVER_PACKAGE = 2;
    public static final int CHAT_MESSAGE_PACKAGE = 3;
    public static final int LOBBY_CHOICE = 11;
    public static final int ROOM = 12;
    public static final int GAME_START = 13;

    private final int id;

    public DataPackage(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
