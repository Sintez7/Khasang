package app;

import java.util.List;

public class DefaultModel implements Model {

    private List<Ship> ships;
    private List<Ship> shipsAlive;

    @Override
    public boolean isGameEnded() {
        return getWinStatus();
    }

    @Override
    public void update() {

    }
    
}
