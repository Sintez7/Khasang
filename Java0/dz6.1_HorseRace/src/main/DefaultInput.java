package main;

import java.util.Scanner;

public class DefaultInput implements UserInput {

    private Scanner scanner = new Scanner(System.in);

    @Override
    public int getInt() {
        int number = 0;
        do
        {
            if (scanner.hasNextInt()) {
                number = scanner.nextInt();
                return number;
            } else {
                System.out.println("Invalid input");
                scanner.nextLine();
            }
        }while (true);
    }
}
