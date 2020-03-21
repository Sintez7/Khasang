package horses;

public interface Horse {
    double getMinSpeed();

    double getMaxSpeed();

    double getCoveredDistance();

    void addCoveredDistance(double distance);

    int getTime();

    void addTime();

    boolean getFinished();

    void setFinished();

    int getNumber();

    void setNumber(int number);
}
