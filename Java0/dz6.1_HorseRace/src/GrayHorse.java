public class GrayHorse implements Horse {

    private double minSpeed;
    private double maxSpeed;
    private double coveredDistance;

    @Override
    public String toString() {
        return "Horse{" +
                "minSpeed=" + minSpeed +
                ", maxSpeed=" + maxSpeed +
                ", coveredDistance=" + coveredDistance +
                '}';
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
}
