public class MainApp {

    private static final int DEFAULT_HORSES_COUNT = 5;
    private static final TrackType[] TRACK_TYPES = TrackType.values();
    private static final int MIN_HORSES_TO_RACE = 2;
    private static final int MAX_HORSES_TO_RACE = 10;

    TrackFactory trackFactory = new TrackFactory();
    RaceManager raceManager = new RaceManager();
    UserInput input = new DefaultInput();

    TrackType trackType;


    public void start() {
        int horsesCount = getHorsesCount();
        trackType = getTrackType();
        Track track = trackFactory.buildTrack(trackType);

        raceManager.invokeRaceStart(track, horsesCount, input);
    }

    private TrackType getTrackType() {
        TrackType result = null;
        int number;
        do
        {
            System.out.println("Choose track type:");
            showTrackTypes();
            number = input.getInt();

            if (number > TRACK_TYPES.length || number < 1) {
                System.out.println("Invalid track type number! Try again");
            } else {
                //TODO: Поменять свич на что-нибудь по лучше
                switch (number) {
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
                break;
            }

        } while (true);

        return result;
    }

    private int getHorsesCount() {
        int number = 0;
        System.out.println("Enter horses count");

        do
        {
            number = input.getInt();
            if (number > MAX_HORSES_TO_RACE || number < MIN_HORSES_TO_RACE) {
                System.out.println("Invalid horses number! Try again");
            } else {
                break;
            }
        } while (true);

        return number;
    }

    private void showTrackTypes() {
        for (TrackType t : TRACK_TYPES) {
            System.out.println("№ " + (t.ordinal() + 1) + ": " + t.getTrackName());
        }
    }
}
