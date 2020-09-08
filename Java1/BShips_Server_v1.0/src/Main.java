public class Main {

    public static void main(String[] args) {
        Field field = new Field();
//        field.printField();
//        System.out.println(field.placeShip(1,1, new Ship(1)));
//        field.printField();
//        System.out.println(field.placeShip(2,2, new Ship(1)));
//        field.printField();
//        System.out.println(field.placeShip(2,2, new Ship(1)));
//        field.printField();

        System.out.println(field.placeShip(5,5, new Ship(4, Ship.Bias.UP)));
        field.printField();
        System.out.println(field.placeShip(8,8, new Ship(4, Ship.Bias.UP)));
        field.printField();
        System.out.println(field.placeShip(2,8, new Ship(4, Ship.Bias.DOWN)));
        field.printField();
    }
}
