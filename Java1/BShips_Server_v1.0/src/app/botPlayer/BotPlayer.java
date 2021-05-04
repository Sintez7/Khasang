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
        self.setName("BotThread");
        self.start();
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
                    case DataPackage.PLAYER_WON -> handlePlayerWon();
                    case DataPackage.REMATCH_OFFER -> handleRematchOffer();
                    case DataPackage.REMATCH_SIGNAL -> handleRematch();
                    default -> System.err.println("Unknown package: " + in.toString());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                alive = false;
            }
        }
    }

    private void handlePlayerWon() {
        bai.setGameEnded();
    }

    private void handlePlayerInfo(PlayerInfo in) {
        System.err.println("bPlayerInfo");
        thisBotPlayerNumber = in.getPlayerInfo();
        System.err.println("thisBotPlayerNumber: " + thisBotPlayerNumber);
    }

    private void handleGameStart() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        System.err.println("b BattleStart package");
    }

    private void handleHitResponse(HitResponse in) {
        System.err.println("b HitResponse package");
        bai.handleHitResponse(in);
    }

    private void handleTurnUpdate(TurnUpdate in) {
        System.err.println("b TurnUpdate package");
        System.err.println("thisBotPlayerNumber " + thisBotPlayerNumber);
        System.err.println("player move number: " + in.getPlayerTurn());
        bai.handleTurnUpdate(in);
        if (in.getPlayerTurn() == thisBotPlayerNumber) {
            System.err.println("Bot allowed to shoot");
            bai.shoot();
        }
    }

    private void handleRematchOffer() {
        System.err.println("b rematchOffer package");
        currentGameServer.handleRematchDecision(this, new RematchDecision(true));
    }

    private void handleRematch() {
        spai.prepareToRematch();
        bai.prepareToRematch();
        handleGameStart();
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
        try {
            sendData(new PlaceShipResponse(currentGameServer.handlePlaceShip(this, new PlaceShip(x, y, size, bias))));
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void allShipsPlaced() {
        currentGameServer.playerReady(this);
    }

    public void handleShoot(Hit hit) {
        currentGameServer.handleHit(this, hit.getX(), hit.getY());
    }

    public Thread getBotThread() {
        return self;
    }

    public enum State {
        GAME_STARTED,
        SHIP_PLACEMENT_PHASE,
        BATTLE_PHASE,
        REMATCH_PHASE
    }
}
