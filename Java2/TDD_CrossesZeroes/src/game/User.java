package game;

public class User {
    private ShootStrategy shootStrategy;
    private char sign;

    public User(ShootStrategy shootStrategy, char sign) {
        this.shootStrategy = shootStrategy;
        this.sign = sign;
    }

    public Point getShootPoint() {
        return shootStrategy.getShootPoint();
    }

    public char getSign() {
        return sign;
    }
}
