package app;

import app.shared.HitResponse;
import app.shared.PlaceShip;

import java.net.SocketException;

public class Game {

    public final Object REMATCH_MONITOR = new Object();
    public volatile boolean rematch;

    private Player player1;
    private Player player2;

    private Field player1Field = new Field();
    private FakeField player1OpponentField = new FakeField();
    private Field player2Field = new Field();
    private FakeField player2OpponentField = new FakeField();

    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public void prepareRematch() {

    }

    public boolean handlePlaceShip(Player player, PlaceShip ship) {
        if (player.equals(player1)) {
            return tryPlaceShipForPlayer1(ship);
        }

        if (player.equals(player2)) {
            return tryPlaceShipForPlayer2(ship);
        }

        return false;
    }

    private boolean tryPlaceShipForPlayer1(PlaceShip ship) {
        return player1Field.placeShip(ship.getX(), ship.getY(), ship.getSize(), ship.getBias());
    }

    private boolean tryPlaceShipForPlayer2(PlaceShip ship) {
        return player2Field.placeShip(ship.getX(), ship.getY(), ship.getSize(), ship.getBias());
    }

    public void handleHit(Player player, int x, int y) {
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
            case POINT_ALREADY_HIT -> {
                player1.sendData(new HitResponse(HitResponse.ALREADY_HIT));
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
            case POINT_ALREADY_HIT -> player2.sendData(new HitResponse(HitResponse.ALREADY_HIT));
        }

        if (player1Field.checkLose()) {
            setPlayer1Defeated();
        }
    }

    private void setPlayer1Defeated() {

    }

    private void setPlayer2Defeated() {

    }
}
