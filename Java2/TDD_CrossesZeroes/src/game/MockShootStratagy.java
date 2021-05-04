package game;

public class MockShootStratagy implements ShootStrategy {
    @Override
    public Point getShootPoint() {
        return new Point(0, 0);
    }
}
