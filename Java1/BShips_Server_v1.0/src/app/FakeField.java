package app;

public class FakeField extends Field {

    @Override
    public HitResult hit(int x, int y) {
        return null;
    }

    @Override
    public boolean checkLose() {
        return false;
    }

    @Override
    public boolean placeShip(int x, int y, int shipSize, int shipBias) {
        return false;
    }

    public void setPoint(int x, int y, HitResult state) {
        switch (state) {
            case HIT_SHIP -> cells[x][y] = OCCUPIED_HIT;
            case MISS -> cells[x][y] = HIT;
        }
    }

    public void setPoint(int x, int y, int state) {
        cells[x][y] = state;
    }
}
