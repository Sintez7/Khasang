package game;

import java.io.InputStream;
import java.util.Scanner;

public class ConsoleShootStrategy implements ShootStrategy {
    private InputStream inputStream;
    private Scanner scanner;

    public ConsoleShootStrategy() {
        this(System.in);
    }

    public ConsoleShootStrategy(InputStream inputStream) {
        this.inputStream = inputStream;
        scanner = new Scanner(inputStream);
    }

    @Override
    public Point getShootPoint() {
        String s = scanner.nextLine();
        String[] split = s.split(" ");

        return new Point(Integer.valueOf(split[0]), Integer.valueOf(split[1]));
    }
}
