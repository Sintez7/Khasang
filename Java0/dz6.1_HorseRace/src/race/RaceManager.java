package race;

import horses.Horse;
import horses.HorsesStall;
import main.UserInput;
import tracks.Track;

import java.util.*;

public class RaceManager {

    private static final int DISTANCE_RANDOM_MODIFIER = 20;

    private double totalDistance;
    private int place;
    private Horse[] horses;
    private HorsesStall horsesStall = new HorsesStall();


    public void invokeRaceStart(Track track, int horsesCount, UserInput input) {
        setTotalDistance(track);
        prepareHorses(horsesCount);

        int userHorse = getUserBet(input, horsesCount);
        startRace(track);
        showResults(track, userHorse);
    }

    private void setTotalDistance(Track track) {
        if (track.cycled()) {
            totalDistance = track.getLength() * track.cycleCount();
        } else {
            totalDistance = track.getLength();
        }
    }

    private void prepareHorses(int horsesCount) {
        horses = horsesStall.fillStall(horsesCount);
    }

    private int getUserBet(UserInput input, int horsesCount) {
        System.out.println("Enter horse number you bet\n" +
                "choose from " + horsesCount + " horses");
        do
        {
            int result = input.getInt();
            if (result > horsesCount || result < 1) {
                System.out.println("Invalid bet! Try again");
            } else {
                return result;
            }

        } while (true);

    }

    private void startRace(Track track) {

        boolean raceEnded = false;
        int horsesFinished = 0;

        do
        {
            for (Horse h : horses) {
                if (!h.getFinished()) {
                    h.addCoveredDistance(getTickDistance(h));
                    h.addTime();
                }

                if (h.getCoveredDistance() >= totalDistance) {
                    h.setFinished();
                }
            }

            horsesFinished = 0;
            for (Horse h : horses) {

                if (h.getCoveredDistance() < totalDistance) {
                    break;
                } else {
                    horsesFinished++;
                }
            }

            if (horsesFinished == horses.length) {
                raceEnded = true;
            }
        } while (!raceEnded);

    }

    private double getTickDistance(Horse horse) {
        double speed = horse.getMinSpeed() + (Math.random() * DISTANCE_RANDOM_MODIFIER);
        return Math.min(speed, horse.getMaxSpeed());
    }

    private void showResults(Track track, int userHorse) {
        List<Horse> resultList = new ArrayList<>(Arrays.asList(horses));
        resultList.sort(track.getWinComparator());

        for (int i = 0; i < horses.length; i++) {
            System.out.println("Place № " + (i + 1) + " is horse with number " + resultList.get(i).getNumber());
        }

        if (resultList.get(0).getNumber() == userHorse) {
            System.out.println("Congratulations! You Won!");
        } else {
            System.out.println("You lose! Better luck next time!");
        }
    }
}
