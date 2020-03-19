package horses;

public class GrayHorse implements Horse {

    private static final int MIN_SPEED_RANDOM_MODIFIER = 10;
    private static final int MIN_SPEED_FLAT_MODIFIER = 10;
    private static final int MAX_SPEED_RANDOM_MODIFIER = 30;
    private static final int MAX_SPEED_FLAT_MODIFIER = 21;

    private double minSpeed;
    private double maxSpeed;
    private double coveredDistance = 0.0;
    private int time = 0;
    private int number;

    public boolean finished = false;

    GrayHorse() {
        minSpeed = (Math.random() * MIN_SPEED_RANDOM_MODIFIER) + MIN_SPEED_FLAT_MODIFIER;
        maxSpeed = (Math.random() * MAX_SPEED_RANDOM_MODIFIER) + MAX_SPEED_FLAT_MODIFIER;
    }

    @Override
    public double getMinSpeed() {
        return minSpeed;
    }

    @Override
    public double getMaxSpeed() {
        return maxSpeed;
    }

    @Override
    public double getCoveredDistance() {
        return coveredDistance;
    }

    @Override
    public void addCoveredDistance(double distance) {
        coveredDistance += distance;
    }

    @Override
    public int getTime() {
        return time;
    }

    @Override
    public void addTime() {
        time++;
    }

    @Override
    public boolean getFinished() {
        return finished;
    }

    @Override
    public void setFinished() {
        finished = true;
    }

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public void setNumber(int number) {
        this.number = number;
    }
}
