package app;

import app.shared.HitResponse;
import app.shared.PlaceShip;
import app.shared.TurnUpdate;

import java.net.SocketException;

public class Game implements Runnable {

    private final Thread self;

    public final Object REMATCH_DECISION_MONITOR = new Object();
    public final Object REMATCH_MONITOR = new Object();
    public final Object PLAYERS_READY = new Object();
    public volatile boolean rematch = false;
    private volatile GameState state;

    private Player player1;
    private Player player2;

    private Field player1Field = new Field();
    private FakeField player1OpponentField = new FakeField();
    private Field player2Field = new Field();
    private FakeField player2OpponentField = new FakeField();

    private final Object DEFEATED_MONITOR = new Object();
    private boolean player1Defeated = false;
    private boolean player2Defeated = false;

    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        self = new Thread(this);
        state = GameState.CREATED;
        rematch = true;
    }

    public void start() {
        self.start();
    }

    @Override
    public void run() {
        System.err.println("Game Thread start, rematch is: " + rematch);
        while(rematch) {
            state = GameState.SHIP_PLACEMENT_PHASE;
            System.err.println("Game switched to SHIP_PLACEMENT_PHASE state");

            synchronized (PLAYERS_READY) {
                try {
                    PLAYERS_READY.wait();
                } catch (InterruptedException e) {
                    System.err.println("PLAYERS_READY interrupted in Game");
                }
            }

            state = GameState.BATTLE_PHASE;
            System.err.println("Game switched to BATTLE_PHASE state");
            updateClients();

            synchronized (DEFEATED_MONITOR) {
                try {
                    DEFEATED_MONITOR.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            state = GameState.REMATCH_DECISION;
            System.err.println("Game switched to REMATCH_DECISION state");

            synchronized (REMATCH_DECISION_MONITOR) {
                try {
                    REMATCH_DECISION_MONITOR.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            synchronized (REMATCH_MONITOR) {
                REMATCH_MONITOR.notifyAll();
            }

            System.err.println("rematch: " + rematch);

            if (rematch) {
                prepareRematch();
            }
        }
    }

    public void prepareRematch() {
        player1Field = new Field();
        player1OpponentField = new FakeField();
        player2Field = new Field();
        player2OpponentField = new FakeField();
        player1Defeated = false;
        player2Defeated = false;
        updateClients();
    }

    public boolean handlePlaceShip(Player player, PlaceShip ship) {
        System.err.println("handlePlaceShip in Game invoked");
        System.err.println("placeShip: " + ship.toString());
        if (player.equals(player1)) {
            return tryPlaceShipForPlayer1(ship);
        } else {
            System.err.println("player not equals player1");
        }

        if (player.equals(player2)) {
            return tryPlaceShipForPlayer2(ship);
        }else {
            System.err.println("player not equals player2");
        }
        updateClients();
        return false;
    }

    private boolean tryPlaceShipForPlayer1(PlaceShip ship) {
        if (state == GameState.SHIP_PLACEMENT_PHASE) {
            return player1Field.placeShip(ship.getX(), ship.getY(), ship.getSize(), ship.getBias());
        } else {
            System.err.println("gameState is not SHIP_PLACEMENT_PHASE for player1");
            return false;
        }
    }

    private boolean tryPlaceShipForPlayer2(PlaceShip ship) {
        if (state == GameState.SHIP_PLACEMENT_PHASE) {
            return player2Field.placeShip(ship.getX(), ship.getY(), ship.getSize(), ship.getBias());
        } else {
            System.err.println("gameState is not SHIP_PLACEMENT_PHASE for player2");
            return false;
        }
    }

    public void handleHit(Player player, int x, int y) {
        if (state == GameState.BATTLE_PHASE) {
            if (player.equals(player1)) {
                try {
                    handlePlayer1Hit(x, y);
                } catch (SocketException e) {
                    System.err.println("handlePlayer1Hit exception");
                }
            }

            if (player.equals(player2)) {
                try {
                    handlePlayer2Hit(x, y);
                } catch (SocketException e) {
                    System.err.println("handlePlayer2Hit exception");
                }
            }
        }
    }

    private void handlePlayer1Hit(int x, int y) throws SocketException {
        switch (player2Field.hit(x, y)) {
            case HIT_SHIP -> {
                player1OpponentField.setPoint(x, y, Field.HitResult.HIT_SHIP);
                player1.sendData(new HitResponse(HitResponse.HIT_SHIP));
            }
            case MISS -> {
                player1OpponentField.setPoint(x, y, Field.HitResult.MISS);
                player1.sendData(new HitResponse(HitResponse.MISS));
            }
        }

        if (player2Field.checkLose()) {
            setPlayer2Defeated();
        }

        player2Field.checkSunkShips();
//        updatePlayers();
//        updateSpectators();
    }

    private void handlePlayer2Hit(int x, int y) throws SocketException {
        switch (player1Field.hit(x, y)) {
            case HIT_SHIP -> player2.sendData(new HitResponse(HitResponse.HIT));
            case MISS -> player2.sendData(new HitResponse(HitResponse.MISS));
//            case POINT_ALREADY_HIT -> player2.sendData(new HitResponse(HitResponse.ALREADY_HIT));
        }

        if (player1Field.checkLose()) {
            setPlayer1Defeated();
        }
    }

    private void setPlayer1Defeated() {
        player1Defeated = true;
        synchronized (DEFEATED_MONITOR) {
            DEFEATED_MONITOR.notifyAll();
        }
    }

    private void setPlayer2Defeated() {
        player2Defeated = true;
        synchronized (DEFEATED_MONITOR) {
            DEFEATED_MONITOR.notifyAll();
        }
    }

    public void updateClients() {
        try {
            player1.sendData(new TurnUpdate(player1Field.cells, player1OpponentField.cells, 0));
            player2.sendData(new TurnUpdate(player2Field.cells, player2OpponentField.cells, 0));
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    private enum GameState {
        CREATED,
        SHIP_PLACEMENT_PHASE,
        BATTLE_PHASE,
        REMATCH_DECISION
    }
}
