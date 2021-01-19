package app.botPlayer;

import app.GameServer;
import app.LobbyRoom;
import app.Player;
import app.shared.DataPackage;
import app.shared.PlaceShip;
import app.shared.PlaceShipResponse;

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

    private boolean alive = true;
    private BotPlayerSPAI spai = new BotPlayerSPAI(this);
    private State state = State.GAME_STARTED;

    public BotPlayer() {

    }

    @Override
    public void run() {
        while(alive) {
            try {
                DataPackage in = queue.take();
                switch (in.getId()) {
                    case DataPackage.GAME_START -> handleGameStart();
                    case DataPackage.PLACE_SHIP_RESPONSE -> handlePlaceShipResponse((PlaceShipResponse) in);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleGameStart() {
        state = State.SHIP_PLACEMENT_PHASE;
        spai.next();
    }

    private void handlePlaceShipResponse(PlaceShipResponse in) {
        spai.handleResponse(in);
        spai.next();
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

    public void sendPlaceShip(int x, int y, int size, int bias) {
        currentGameServer.handlePlaceShip(this, new PlaceShip(x, y, size, bias));
    }

    public enum State {
        GAME_STARTED,
        SHIP_PLACEMENT_PHASE,
        BATTLE_PHASE,
        REMATCH_PHASE
    }
}
