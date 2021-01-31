package app.botPlayer;

import app.GameServer;
import app.LobbyRoom;
import app.Player;
import app.shared.*;

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
    private BotPlayerBAI bai = new BotPlayerBAI(this);
    private State state = State.GAME_STARTED;

    private int thisBotPlayerNumber = -1;

    public BotPlayer() {

    }

    @Override
    public void run() {
        System.err.println("BotPlayer method run() started");
        while(alive) {
            System.err.println("BotPlayer new cycle iteration");
            try {
                DataPackage in = queue.take();
                System.err.println("BotPlayer received package");
                switch (in.getId()) {
                    case DataPackage.PLAYER_INFO -> handlePlayerInfo((PlayerInfo) in);
                    case DataPackage.GAME_START -> handleGameStart();
                    case DataPackage.PLACE_SHIP_RESPONSE -> handlePlaceShipResponse((PlaceShipResponse) in);
                    case DataPackage.BATTLE_START -> handleBattleStart();
                    case DataPackage.HIT_RESPONSE -> handleHitResponse((HitResponse) in);
                    case DataPackage.TURN_UPDATE -> handleTurnUpdate((TurnUpdate) in);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void handlePlayerInfo(PlayerInfo in) {
        System.err.println("bPlayeerInfo");
        thisBotPlayerNumber = in.getPlayerInfo();
        System.err.println("thisBotPlayerNumber: " + thisBotPlayerNumber);
    }

    private void handleGameStart() {
        System.err.println("bp GameStartPackage");
        state = State.SHIP_PLACEMENT_PHASE;
        spai.next();
    }

    private void handlePlaceShipResponse(PlaceShipResponse in) {
        System.err.println("bp PlaceShipResponse package");
        spai.handleResponse(in);
        spai.next();
    }

    private void handleBattleStart() {

    }

    private void handleHitResponse(HitResponse in) {
        bai.handleHitResponse(in);
    }

    private void handleTurnUpdate(TurnUpdate in) {
        bai.handleTurnUpdate(in);
        if (in.getPlayerTurn() == thisBotPlayerNumber) {
            bai.shoot();
        }
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

    public void allShipsPlaced() {

    }

    public enum State {
        GAME_STARTED,
        SHIP_PLACEMENT_PHASE,
        BATTLE_PHASE,
        REMATCH_PHASE
    }
}
