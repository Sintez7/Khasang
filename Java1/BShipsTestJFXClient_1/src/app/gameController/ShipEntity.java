package app.gameController;

public class ShipEntity {
    // Описание характеристик
    int x;
    int y;
    int size;
    int bias;

    public ShipEntity(int x, int y, int shipSize, int shipBias) {
        this.x = x;
        this.y = y;
        size = shipSize;
        bias = shipBias;
    }
}
