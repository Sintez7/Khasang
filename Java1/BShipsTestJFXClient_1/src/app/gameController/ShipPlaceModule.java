package app.gameController;

import app.Main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class ShipPlaceModule {

    public static final int ONE_DECK_SHIPS_MAX = 4;
    public static final int TWO_DECK_SHIPS_MAX = 3;
    public static final int THREE_DECK_SHIPS_MAX = 2;
    public static final int FOUR_DECK_SHIPS_MAX = 1;

    private GameController owner;
    private ArrayList<ShipEntity> shipsToPlace = new ArrayList<>();
    private Random random = new Random();
    private ShipEntity previousShip;
    private boolean autoPlacedShip = false;

    public ShipPlaceModule(GameController gameController) {

        owner = gameController;
        fillList();
    }

    private void fillList() {
        shipsToPlace.add(new ShipEntity(0, 0, 4, 1));

        shipsToPlace.add(new ShipEntity(0, 0, 3, 1));
        shipsToPlace.add(new ShipEntity(0, 0, 3, 1));

        shipsToPlace.add(new ShipEntity(0, 0, 2, 1));
        shipsToPlace.add(new ShipEntity(0, 0, 2, 1));
        shipsToPlace.add(new ShipEntity(0, 0, 2, 1));

        shipsToPlace.add(new ShipEntity(0, 0, 1, 1));
        shipsToPlace.add(new ShipEntity(0, 0, 1, 1));
        shipsToPlace.add(new ShipEntity(0, 0, 1, 1));
        shipsToPlace.add(new ShipEntity(0, 0, 1, 1));
    }

    public void placeShipManual(int x, int y, int shipSize, int shipBias) {
        previousShip = new ShipEntity(x, y, shipSize, shipBias);
        placeShip(x, y, shipSize, shipBias);
    }

    public void placeShipAuto() {
        System.err.println("auto place ship invoked");
        System.err.println("current list size is " + shipsToPlace.size());
        if (isAllShipsPlaced()) {
            System.err.println("list size: empty, all ships placed");
        } else {
            int x = random.nextInt(10);
            int y = random.nextInt(10);
            int bias = random.nextInt(4) + 1;
            previousShip = shipsToPlace.get(0);
            previousShip.bias = bias;
            System.err.println("sending ship to place - x: " + x + ", y: " + y + ", size: " + previousShip.size + ", bias: " + bias);
            placeShip(x, y, previousShip.size, bias);
            previousShip = new ShipEntity(x, y, previousShip.size, bias);
            autoPlacedShip = true;
        }
    }

    private void placeShip(int x, int y, int shipSize, int shipBias) {
        owner.sendPlaceShip(x, y, shipSize, shipBias);
    }

    public void handlePlaceShipResponse(boolean accepted) {
        if (accepted) {
            handlePositiveSPR();
            owner.drawPlacedShip(previousShip);
        } else {
            handleNegativeSPR();
        }

        if (autoPlacedShip) {
            placeShipAuto();
        }
    }

    private void handlePositiveSPR() {
        ShipEntity temp;
        Iterator<ShipEntity> i = shipsToPlace.iterator();
        while (i.hasNext()) {
            temp = i.next();
            if (temp.size == previousShip.size) {
                i.remove();
                break;
            }
        }
    }

    private void handleNegativeSPR() {
//        owner.applyToChat("Ship placement denied.");
    }

    public void prepareToRematch() {
        shipsToPlace.clear();
        fillList();
    }

    public boolean oneDeckShipsAvailable() {
        for (ShipEntity s : shipsToPlace) {
            if (s.size == 1) {
                return true;
            }
        }
        return false;
    }

    public boolean twoDeckShipAvailable() {
        for (ShipEntity s : shipsToPlace) {
            if (s.size == 2) {
                return true;
            }
        }
        return false;
    }

    public boolean threeDeckShipAvailable() {
        for (ShipEntity s : shipsToPlace) {
            if (s.size == 3) {
                return true;
            }
        }
        return false;
    }

    public boolean fourDeckShipAvailable() {
        for (ShipEntity s : shipsToPlace) {
            if (s.size == 4) {
                return true;
            }
        }
        return false;
    }

    public boolean isAllShipsPlaced() {
        return shipsToPlace.size() == 0;
    }
}
