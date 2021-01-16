package app;

import app.shared.DataPackage;

import java.net.SocketException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BotPlayer implements Player, Runnable{

    private Thread self = new Thread(this);
    private BlockingQueue<DataPackage> queue = new LinkedBlockingQueue<>();

    private int id = -5;
    private String name = "Alien conqueror. lvl 1";

    private volatile LobbyRoom currentRoom;
    private volatile GameServer currentGameServer;

    public BotPlayer() {

    }

    @Override
    public void run() {

    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void sendData(DataPackage dataPackage) throws SocketException {
        queue.add(dataPackage);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setCurrentRoom(LobbyRoom currentRoom) {
        this.currentRoom = currentRoom;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setCurrentGameServer(GameServer gameServer) {
        currentGameServer = gameServer;
    }
}
