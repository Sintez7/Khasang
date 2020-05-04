package app;

import java.util.List;

public class DefaultModel implements Model {

    private List<IShip> ships;
    private List<IShip> shipsAlive;

    @Override
    public boolean isGameEnded() {
        return getWinStatus();
    }

    @Override
    public void update() {

    }
    
}
