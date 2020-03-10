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
        double temp = 10.0f + (Math.random() * 10);
        sharedBowl -= temp;
        foodConsumed += temp;
        System.out.println("shared food consumed amount = " + temp);
        System.out.println("shared food left = " + sharedBowl);
    }

    public void drinkStatic() {
        double temp = 10.0f + (Math.random() * 10);
        sharedPlate -= temp;
        waterConsumed += temp;
        System.out.println("shared water consumed amount = " + temp);
        System.out.println("shared water left = " + sharedPlate);
    }

    public void eat(Bowl bowl) {
        double temp = 10.0f + (Math.random() * 10);
        bowl.foodCount -= temp;
        foodConsumed += temp;
        System.out.println("private food consumed = " + temp);
        System.out.println("private food left = " + bowl.foodCount);
    }

    public void drink(Plate plate) {
        double temp = 10.0f + (Math.random() * 10);
        plate.waterCount -= temp;
        waterConsumed += temp;
        System.out.println("private water consumed = " + temp);
        System.out.println("private water left = " + plate.waterCount);
    }

    public void info() {
        System.out.println("cat number is " + number);
        System.out.println("total food consumed = " + foodConsumed);
        System.out.println("total water consumed = " + waterConsumed);
    }
}
