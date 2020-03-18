public class RaceManager {

    private double totalDistance;
    private int place;
    private Horse[] horses;
    private HorsesStall horsesStall = new HorsesStall();


    public void invokeRaceStart(Track track, int horsesCount, UserInput input) {
        setTotalDistance(track);
        prepareHorses(horsesCount);

        int userHorse = getUserBet(input, horsesCount);
        startRace();
        showResults(userHorse);
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

    //TODO: Допилить класс
    private void startRace() {

    }

    private void showResults(int userHorse) {

    }
}
