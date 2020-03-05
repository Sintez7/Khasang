import java.util.Scanner;

public class SeaBattle {

    static int posCount = 20;
    static char[] cells = new char[posCount];
    static final char SHIP_PICTURE = 'X';
    static final char FREE_CELL_PICTURE = '.';
    static final char MISS_PICTURE = '*';
    static final int DEBRIS_PICTURE = '#';

    static int shipLength = 3;
    static int shipsCount = 3;
    static int aliveCells = 0;

    public static void main(String[] args) {
        launchGame();
    }

    private static void launchGame() {

        for (int i = 0; i < cells.length; i++) {
            cells[i] = FREE_CELL_PICTURE;
        }
        placeShips();
        Scanner scanner = new Scanner(System.in);

        do
        {
            System.out.println(cells);
            int shoot = 0;
            while (true) {
                if (scanner.hasNextInt()) {
                    shoot = scanner.nextInt();
                    break;
                } else {
                    String temp = scanner.nextLine();
                }
            }
            System.out.println("Ваш выстрел " + shoot);

            switch (cells[shoot]) {
                case FREE_CELL_PICTURE:
                    System.out.println("Промах");
                    cells[shoot] = MISS_PICTURE;
                    break;
                case SHIP_PICTURE :
                    System.out.println("Попадание");
                    cells[shoot] = DEBRIS_PICTURE;
                    aliveCells--;
                    break;
                case MISS_PICTURE :
                    System.out.println("Уже стреляли");
                    break;
                default:
            }
        } while (aliveCells > 0);
        System.out.println("Победа");
        System.out.println(cells);
    }

    private static void placeShips() {
        int shipPosition = (int) Math.floor(Math.random() * 10);

        for (int j = 0; j < shipsCount; j++) {
            if (shipPosition <= cells.length - shipLength) {
                for (int i = 0; i < shipLength; i++) {
                    cells[shipPosition + i] = SHIP_PICTURE;
                    aliveCells++;
                }
                shipPosition += shipLength + 1;
                if (shipPosition > cells.length - shipLength) {
                    shipPosition = 0;
                }
            } else {
                shipPosition = 0;
                j--;
            }
        }
    }
}
