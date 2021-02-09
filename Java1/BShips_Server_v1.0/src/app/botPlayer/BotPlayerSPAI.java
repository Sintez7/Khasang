package app.botPlayer;

import app.Ship;
import app.shared.PlaceShipResponse;

import java.util.ArrayList;
import java.util.Random;

public class BotPlayerSPAI {
    /*
     * Ship Placement Artificial Intelligence //lol
     * AI для расстановки кораблей
     */
    private final BotPlayer owner;
    private final ArrayList<Ship> shipSet = new ArrayList<>();
    private final ArrayList<Ship> shipsToPlace = new ArrayList<>();
    private final Random random = new Random();

    private Ship previousShip;

    public BotPlayerSPAI(BotPlayer botPlayer) {
        owner = botPlayer;
        initShipSet();
    }

    public void prepareToRematch() {
        shipSet.clear();
        shipsToPlace.clear();
        initShipSet();
    }

    private void initShipSet() {
        shipSet.add(new Ship(4, Ship.RIGHT));

        shipSet.add(new Ship(3, Ship.RIGHT));
        shipSet.add(new Ship(3, Ship.RIGHT));

        shipSet.add(new Ship(2, Ship.RIGHT));
        shipSet.add(new Ship(2, Ship.RIGHT));
        shipSet.add(new Ship(2, Ship.RIGHT));

        shipSet.add(new Ship(1, Ship.RIGHT));
        shipSet.add(new Ship(1, Ship.RIGHT));
        shipSet.add(new Ship(1, Ship.RIGHT));
        shipSet.add(new Ship(1, Ship.RIGHT));

        shipsToPlace.addAll(shipSet);
    }

    public void next() {
        System.err.println("bSPAI next()");
        System.err.println("current list size is " + shipsToPlace.size());
        if (shipsToPlace.size() > 0) {
            int x = random.nextInt(10);
            int y = random.nextInt(10);
            int bias = random.nextInt(4) + 1;
            previousShip = shipsToPlace.get(0);
            previousShip.setBias(bias);
            System.err.println("sending ship to place- x: " + x + ", y: " + y + ", size: " + previousShip.getSize() + ", bias: " + bias);
            owner.sendPlaceShip(x, y, previousShip.getSize(), bias);
        } else {
            System.err.println("list size: empty");
            owner.allShipsPlaced();
        }
    }

    public void handleResponse(PlaceShipResponse in) {
        System.err.println("bSPAI handleResponse(), response: " + in.getResponse());
        if (in.getResponse()) {
            shipsToPlace.remove(0);
        }
    }
}
