import java.util.Scanner;

public class MainApp {

    private static final int DEFAULT_HORSES_COUNT = 5;
    private static final TrackType[] TRACK_TYPES = TrackType.values();
    private static final int MIN_HORSES_TO_RACE = 2;
    private static final int MAX_HORSES_TO_RACE = 10;

    TrackFactory trackFactory = new TrackFactory();
    RaceManager raceManager = new RaceManager();

    TrackType trackType;
    int horsesCount;

    public void start() {
        trackType = getTrackTypeUserChoice();
        horsesCount = getHorsesCountUserChoice();

        raceManager.invokeRace(trackType, horsesCount);
    }

    private TrackType getTrackTypeUserChoice() {
        Scanner scanner = new Scanner(System.in);
        TrackType result = null;
        int number;
        do {
            System.out.println("Choose track type:");
            showTrackTypes();
            scanner.nextLine();
            number = scanner.nextInt();

            if (number > TRACK_TYPES.length + 1 || number < 1) {
                System.out.println("Invalid track type number! Try again");
            } else {
                switch (number){
                    case 1:
                        result = TrackType.SPRINT;
                        break;
                    case 2:
                        result = TrackType.ROUND;
                        break;
                    case 3:
                        result = TrackType.TIME_ATTACK;
                        break;
                }
            }

        }while (result == null);

        return result;
    }

    private int getHorsesCountUserChoice() {
        Scanner scanner = new Scanner(System.in);
        int number = 0;
        do{
            scanner.nextLine();
            number = scanner.nextInt();
            if (number > MAX_HORSES_TO_RACE || number < MIN_HORSES_TO_RACE) {
                System.out.println("Invalid horses number! Try again");
                number = 0;
            } else {
                break;
            }
        }while (number == 0);

        return number;
    }

    private void showTrackTypes() {
        for (TrackType t : TRACK_TYPES) {
            System.out.println("№ " + (t.ordinal() + 1) + t.name().toLowerCase());
        }
    }
}
