package horses;

public class HorsesStall {

    private HorseFactory horseFactory = new HorseFactory();

    public Horse[] fillStall(int horsesCount) {
        int number = 1;
        Horse[] result = new Horse[horsesCount];
        HorseType horseType = HorseType.DEFAULT;

        for (int i = 0; i < horsesCount; i++) {
            result[i] = horseFactory.createHorse(horseType);
            result[i].setNumber(number++);
        }

        return result;
    }
}
