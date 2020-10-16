package app;

import app.shared.PlaceShip;

public class Game {

    public final Object REMATCH_MONITOR = new Object();
    public volatile boolean rematch;

    private Player player1;
    private Player player2;

    private Field player1Field = new Field();
    private Field player1OpponentField = new Field();
    private Field player2Field = new Field();
    private Field player2OpponentField = new Field();

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

    public boolean handleHit(Player player, int x, int y) {
        boolean hit;
        if (player.equals(player1)) {
            Field.HitResult result = player1Field.hit(x, y);
            switch (result) {
                case HIT_SHIP -> hit = true;
                case POINT_ALREADY_HIT -> hit = false;
            }
        }

        if (player.equals(player2)) {
            Field.HitResult result = player2Field.hit(x, y);
        }

        return hit;
    }
}
