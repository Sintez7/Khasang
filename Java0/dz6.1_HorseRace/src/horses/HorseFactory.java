package horses;

import horses.types.BulatHorse;
import horses.types.DefaultHorse;
import horses.types.GrayHorse;

public class HorseFactory {

    public Horse createHorse(HorseType type) {
        switch (type) {
            case GRAY:
                return new GrayHorse();
            case BULAT:
                return new BulatHorse();
            default:
                return new DefaultHorse();
        }
    }
}
