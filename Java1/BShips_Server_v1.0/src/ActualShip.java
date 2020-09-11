import java.util.HashMap;
import java.util.Map;

public class ActualShip {

    public static final int ALIVE = 1;
    public static final int DEAD = 0;

    private final Map<Point, Integer> occupiedPoints = new HashMap<>();

    public ActualShip(int x, int y, Ship ship) {
        Point temp = new Point();
        for (int i = 0; i < ship.getSize(); i++) {
            temp.set(x + (ship.getVector().x * i), y + (ship.getVector().y * i));
            occupiedPoints.put(temp, ALIVE);
        }
    }

    public boolean isAlive() {
        boolean result = false;
        for (Integer value : occupiedPoints.values()) {
            if (value == ALIVE) {
                result = true;
                break;
            }
        }
        return result;
    }

    public boolean hit(int x, int y) {
        Point target = new Point (x, y);
        for (Point point : occupiedPoints.keySet()) {
            if (point.equals(target)) {
                occupiedPoints.put(point, DEAD);
                return true;
            }
        }
        return false;
    }
}
