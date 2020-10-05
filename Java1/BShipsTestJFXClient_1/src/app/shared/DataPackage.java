package app.shared;

import java.io.Serializable;

public abstract class DataPackage implements Serializable {

    public static final int LOBBY_PACKAGE = 1;
    public static final int GAMESERVER_PACKAGE = 2;
    public static final int CHAT_MESSAGE_PACKAGE = 3;

    private final int id;

    public DataPackage(int id) {
        this.id = id;
    }
}
