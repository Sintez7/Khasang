public class HorsesStall {

    private HorseFactory horseFabric = new HorseFactory();

    public Horse[] fillStall(int horsesCount) {
        Horse[] result = new Horse[horsesCount];
        HorseType horseType = HorseType.DEFAULT;

        for (int i = 0; i < horsesCount; i++) {
            result[i] = horseFabric.createHorse(horseType);
        }

        return result;
    }
}
