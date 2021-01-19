package app.botPlayer;

import app.Ship;
import app.shared.PlaceShipResponse;

import java.util.ArrayList;

public class BotPlayerSPAI {
    /*
     * AI для расстановки кораблей
     */
    private final BotPlayer owner;
    private final ArrayList<Ship> shipSet = new ArrayList<>();
    private final ArrayList<Ship> shipsToPlace = new ArrayList<>();

    public BotPlayerSPAI(BotPlayer botPlayer) {
        owner = botPlayer;
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
        owner.sendPlaceShip();
    }

    public void handleResponse(PlaceShipResponse in) {

    }
}
