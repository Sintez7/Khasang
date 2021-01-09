package app;

import app.shared.*;

import java.net.SocketException;

public class Game implements Runnable {

    private final Thread self;

    public final Object REMATCH_DECISION_MONITOR = new Object();
    public final Object REMATCH_MONITOR = new Object();
    public final Object PLAYERS_READY = new Object();
    public volatile boolean rematch;
    private volatile GameState state;

    private static final int PLAYER_1 = 1;
    private static final int PLAYER_2 = 2;

    private Player player1;
    private Player player2;

    private volatile Field player1Field = new Field();
    private volatile FakeField player1OpponentField = new FakeField();
    private volatile Field player2Field = new Field();
    private volatile FakeField player2OpponentField = new FakeField();

    private final Object DEFEATED_MONITOR = new Object();
    private boolean player1Defeated = false;
    private boolean player2Defeated = false;

    private boolean player1Turn = true;

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
        sendPlayerInfo();
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

            prepareToBattle();
            state = GameState.BATTLE_PHASE;
            System.err.println("Game switched to BATTLE_PHASE state");

            synchronized (DEFEATED_MONITOR) {
                try {
                    DEFEATED_MONITOR.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            try {
                player1.sendData(new RematchOffer());
                player2.sendData(new RematchOffer());
            } catch (SocketException e) {
                e.printStackTrace();
            }

            state = GameState.REMATCH_DECISION; //TODO: сделать возможность переигровки
            System.err.println("Game switched to REMATCH_DECISION state");

            synchronized (REMATCH_DECISION_MONITOR) {
                try {
                    REMATCH_DECISION_MONITOR.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (rematch) {
                prepareRematch();
            }

            synchronized (REMATCH_MONITOR) {
                REMATCH_MONITOR.notifyAll();
            }

            System.err.println("rematch: " + rematch);
        }
    }

    private void prepareToBattle() {
        System.err.println("PREPARE TO BATTLE STAGE ============================");
        System.err.println("player1 field");
        player1Field.printField();
        System.err.println("player1 opponent field");
        player1OpponentField.printField();
        System.err.println("player 2 field");
        player2Field.printField();
        System.err.println("player2 opponent field");
        player2OpponentField.printField();
        updateClients();
        System.err.println("END OF PREPARING BATTLE ===========================");
    }

    private void sendPlayerInfo() {
        try {
            player1.sendData(new PlayerInfo(PLAYER_1, player1.getName(), player2.getName()));
            player2.sendData(new PlayerInfo(PLAYER_2, player2.getName(), player1.getName()));
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void prepareRematch() {
        player1Field = new Field();
        player1OpponentField = new FakeField();
        player2Field = new Field();
        player2OpponentField = new FakeField();
        player1Defeated = false;
        player2Defeated = false;

        try {
            player1.sendData(new RematchSignal());
            player2.sendData(new RematchSignal());
        } catch (SocketException e) {
            e.printStackTrace();
        }

        updateClients();
    }

    public synchronized boolean handlePlaceShip(Player player, PlaceShip ship) {
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

    public synchronized void handleHit(Player player, int x, int y) {
        if (state == GameState.BATTLE_PHASE) {
            if (player.equals(player1)) {
                try {
                    System.err.println("========== handling player1 hit ===================");
                    handlePlayer1Hit(x, y);
                    System.err.println("========== player 1 hit handled ===================");
                } catch (SocketException e) {
                    System.err.println("handlePlayer1Hit exception");
                }
            }

            if (player.equals(player2)) {
                try {
                    System.err.println("========== handling player2 hit ===================");
                    handlePlayer2Hit(x, y);
                    System.err.println("========== player 2 hit handled ===================");
                } catch (SocketException e) {
                    System.err.println("handlePlayer2Hit exception");
                }
            }
        }

        player1Turn = !player1Turn;
    }

    private void handlePlayer1Hit(int x, int y) throws SocketException {
        switch (player2Field.hit(x, y)) {
            case HIT_SHIP -> {
                player1OpponentField.setPoint(x, y, Field.HitResult.HIT_SHIP);
                player1.sendData(new HitResponse(HitResponse.HIT));
                player1Turn = !player1Turn;
            }
            case MISS -> {
                player1OpponentField.setPoint(x, y, Field.HitResult.MISS);
                player1.sendData(new HitResponse(HitResponse.MISS));
            }
            case POINT_ALREADY_HIT -> {
                player1.sendData(new HitResponse(HitResponse.ALREADY_HIT));
                player1Turn = !player1Turn;
            }
        }

        if (player2Field.checkLose()) {
            System.err.println("registered defeat of player2");
            setPlayer2Defeated();
        }

        player2Field.checkSunkShips(player1OpponentField);
    }

    private void handlePlayer2Hit(int x, int y) throws SocketException {
        switch (player1Field.hit(x, y)) {
            case HIT_SHIP -> {
                player2OpponentField.setPoint(x, y, Field.HitResult.HIT_SHIP);
                player2.sendData(new HitResponse(HitResponse.HIT));
                player1Turn = !player1Turn;
            }
            case MISS -> {
                player2OpponentField.setPoint(x, y, Field.HitResult.MISS);
                player2.sendData(new HitResponse(HitResponse.MISS));
            }
            case POINT_ALREADY_HIT -> {
                player2.sendData(new HitResponse(HitResponse.ALREADY_HIT));
                player1Turn = !player1Turn;
            }
        }

        if (player1Field.checkLose()) {
            System.err.println("registered defeat of player1");
            setPlayer1Defeated();
        }

        player1Field.checkSunkShips(player2OpponentField);
    }

    private void setPlayer1Defeated() {
        System.err.println("Player1 Defeated");
        player1Defeated = true;
        try {
            player1.sendData(new PlayerWon(2));
            player2.sendData(new PlayerWon(2));
        } catch (SocketException e) {
            e.printStackTrace();
        }
        synchronized (DEFEATED_MONITOR) {
            DEFEATED_MONITOR.notifyAll();
        }
    }

    private void setPlayer2Defeated() {
        System.err.println("Player2 Defeated");
        player2Defeated = true;
        try {
            player1.sendData(new PlayerWon(1));
            player2.sendData(new PlayerWon(1));
        } catch (SocketException e) {
            e.printStackTrace();
        }
        synchronized (DEFEATED_MONITOR) {
            DEFEATED_MONITOR.notifyAll();
        }
    }

    public synchronized void updateClients() {

        System.err.println("TurnUpdate sending: \n");
        System.err.println("player1 field");
        player1Field.printField();
        System.err.println("player1 opponent field");
        player1OpponentField.printField();
        System.err.println("player 2 field");
        player2Field.printField();
        System.err.println("player2 opponent field");
        player2OpponentField.printField();
        try {
            player1.sendData(new TurnUpdate(player1Field.cells, player1OpponentField.cells, getPlayerTurn()));
            player2.sendData(new TurnUpdate(player2Field.cells, player2OpponentField.cells, getPlayerTurn()));
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    private int getPlayerTurn() {
        return player1Turn ? 1 : 2;
    }

    private enum GameState {
        CREATED,
        SHIP_PLACEMENT_PHASE,
        BATTLE_PHASE,
        REMATCH_DECISION
    }
}
