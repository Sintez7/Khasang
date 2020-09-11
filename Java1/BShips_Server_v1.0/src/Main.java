public class Main {

    public static void main(String[] args) {
        Field field = new Field();
//        field.printField();
        System.out.println(field.placeShip(1,1, new Ship(1, Ship.UP)));
        System.out.println(field.placeShip(1,1, new Ship(1, Ship.UP)));
        field.printField();
        System.out.println(field.placeShip(10,10, new Ship(1, Ship.LEFT)));
        field.printField();
        System.out.println(field.placeShip(2,2, new Ship(1, Ship.UP)));
        field.printField();
        System.out.println(field.placeShip(1,2, new Ship(1, Ship.UP)));
        field.printField();
        System.out.println(field.placeShip(2,1, new Ship(1, Ship.UP)));
        field.printField();
//        System.out.println(field.placeShip(2,2, new Ship(1)));
//        field.printField();
//        System.out.println(field.placeShip(2,2, new Ship(1)));
//        field.printField();

//        System.out.println(field.placeShip(5,5, new Ship(4, Ship.UP)));
//        field.printField();
//        System.out.println(field.placeShip(8,8, new Ship(4, Ship.UP)));
//        field.printField();
        System.out.println(field.placeShip(3,7, new Ship(4, Ship.DOWN)));
        field.printField();
        System.out.println(field.placeShip(1,8, new Ship(4, Ship.RIGHT)));
        field.printField();
    }
}
