public class Cat {

    private static int catCounter = 0;
    private static double sharedBowl = 100.0;
    private static double sharedPlate = 100.0;
    private int number;
    private double foodConsumed = 0;
    private double waterConsumed = 0;

    Cat() {
        catCounter++;
        number = catCounter;
    }

    public static double getSharedBowl() {
        return sharedBowl;
    }

    public static double getSharedPlate() {
        return sharedPlate;
    }

    public int getNumber() {
        return number;
    }

    public void eatStatic() {
        double temp = 10.0 + (Math.random() * 10);
        if (sharedBowl > temp) {
            sharedBowl -= temp;
            foodConsumed += temp;
            System.out.println("shared food consumed amount = " + temp);
            System.out.println("shared food left = " + sharedBowl);
        } else {
            System.out.println("not enough food in sharedBowl");
        }

    }

    public void drinkStatic() {
        double temp = 10.0 + (Math.random() * 10);
        if (sharedPlate > temp) {
            sharedPlate -= temp;
            waterConsumed += temp;
            System.out.println("shared water consumed amount = " + temp);
            System.out.println("shared water left = " + sharedPlate);
        } else {
            System.out.println("not enough water in sharedPlate");
        }

    }

    public void eat(Bowl bowl) {
        double temp = 10.0 + (Math.random() * 10);
        if (bowl.foodCount > temp) {
            bowl.foodCount -= temp;
            foodConsumed += temp;
            System.out.println("private food consumed = " + temp);
            System.out.println("private food left = " + bowl.foodCount);
        } else {
            System.out.println("not enough food in bowl");
        }

    }

    public void drink(Plate plate) {
        double temp = 10.0 + (Math.random() * 10);
        if (plate.waterCount > temp) {
            plate.waterCount -= temp;
            waterConsumed += temp;
            System.out.println("private water consumed = " + temp);
            System.out.println("private water left = " + plate.waterCount);
        } else {
            System.out.println("not enough water in plate");
        }

    }

    public void info() {
        System.out.println("cat number is " + number);
        System.out.println("total food consumed = " + foodConsumed);
        System.out.println("total water consumed = " + waterConsumed);
    }
}
