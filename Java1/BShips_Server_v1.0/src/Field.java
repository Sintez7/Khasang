import java.util.Arrays;

public class Field {

    private static final int FREE = 0;
    private static final int OCCUPIED = 1;
    private static final int HIT = 2;

    private static final int SIZE = 10;

    int[][] cells = new int[SIZE][SIZE];

    public Field() {
        initField(SIZE);
    }

    private void initField(int size) {
        for (int i = 0; i < size; i ++) {
            for (int j = 0; j < size; j++) {
                cells[j][i] = FREE;
            }
        }
    }

    public boolean hit(int x, int y) {
        if (cells[x - 1][y - 1] != HIT) {
            cells[x - 1][y - 1] = HIT;
            return true;
        } else {
            return false;
        }
    }

    public boolean placeShip (int x, int y, Ship ship) {

        int[][] temp = cloneField(cells);

        int offsetX = 0, offsetY = 0;

        for (int i = 0; i < ship.getSize(); i ++) {
            switch (ship.getBias()) {
                case UP -> {
                    offsetX = 0;
                    offsetY = -i;}
                case DOWN -> {
                    offsetX = 0;
                    offsetY = i;
                }
                case LEFT -> {
                    offsetX = -i;
                    offsetY = 0;
                }
                case RIGHT -> {
                    offsetX = i;
                    offsetY = 0;
                }
            }

            System.out.println("Offsets: x: " + offsetX + " y: " + offsetY);
            System.out.println("targeted cell index x: " + ((x + offsetX) - 1) + " y: " + ((y + offsetY) - 1));

            if ((x + offsetX) - 1 >= 0 & (y + offsetY) - 1 >= 0 & (x + offsetX) - 1 < SIZE & (y + offsetY) - 1 < SIZE) {
                if (temp[(x + offsetX) - 1][(y + offsetY) - 1] == FREE) {
                    temp[(x + offsetX) - 1][(y + offsetY) - 1] = OCCUPIED;
                } else {
                    System.out.println("failed at: status calc x: " + ((x + offsetX) - 1) + " y: " + ((y + offsetY) - 1));
                    return false;
                }
            } else {
                System.out.println("failed at: bounds calc x: " + ((x + offsetX) - 1) + " y: " + ((y + offsetY) - 1));
                return false;
            }
        }
        cells = temp;
        return true;
    }

    private int[][] cloneField(int[][] field) {
        int[][] result = new int[field.length][];

        for (int i = 0; i < field.length; i++) {
            result[i] = Arrays.copyOf(field[i], field[i].length);
        }

        return result;
    }

    public void printField() {
        for (int i = 0; i < cells.length; i ++) {
            for (int j = 0; j < cells[i].length; j++) {
                System.out.print(cells[j][i] + "\t");
            }
            System.out.println("");
        }
        System.out.println("==============================");
    }
}
