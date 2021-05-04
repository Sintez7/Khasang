package app;

import java.util.Scanner;

public class ConsoleInput implements Input {

    Scanner scanner = new Scanner(System.in);
    @Override
    public int getShot() {
        return scanner.nextInt();
    }
}
