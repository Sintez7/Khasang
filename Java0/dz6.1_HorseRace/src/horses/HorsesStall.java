package horses;

public class HorsesStall {

    private HorseFactory horseFabric = new HorseFactory();

    public Horse[] fillStall(int horsesCount) {
        int number = 1;
        Horse[] result = new Horse[horsesCount];
        HorseType horseType = HorseType.DEFAULT;

        for (int i = 0; i < horsesCount; i++) {
            result[i] = horseFabric.createHorse(horseType);
            result[i].setNumber(number++);
        }

        return result;
    }
}
