package main;

import race.RaceManager;
import tracks.Track;
import tracks.TrackType;

public class MainApp {

    private static final TrackType[] TRACK_TYPES = TrackType.values();
    private static final int MIN_HORSES_TO_RACE = 2;
    private static final int MAX_HORSES_TO_RACE = 10;

    RaceManager raceManager = new RaceManager();
    UserInput input = new DefaultInput();
    TrackType trackType;

    public void start() {
        int horsesCount = getHorsesCount();
        trackType = getTrackType();
        Track track = trackType.getInstance();

        raceManager.invokeRaceStart(track, horsesCount, input);
    }

    private TrackType getTrackType() {
        TrackType result;
        int number;
        do
        {
            System.out.println("Choose track type:");
            showTrackTypes();
            number = input.getInt();

            if (number > TRACK_TYPES.length || number < 1) {
                System.out.println("Invalid track type number! Try again");
            } else {
                result = TRACK_TYPES[number - 1];
                break;
            }

        } while (true);

        return result;
    }

    private int getHorsesCount() {
        int number;
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
