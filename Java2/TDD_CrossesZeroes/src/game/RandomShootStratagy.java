package game;

public class RandomShootStratagy implements ShootStrategy {
    @Override
    public Point getShootPoint() {
        return Point.getRandomPoint();
    }
}
