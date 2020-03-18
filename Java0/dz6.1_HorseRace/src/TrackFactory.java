public class TrackFactory {

    public Track buildTrack(TrackType trackType) {
        switch (trackType) {
            case SPRINT:
                return new Sprint();
            case ROUND:
                return new Round();
            case TIME_ATTACK:
                return new TimeAttack();
            default:
                return null;
        }
    }
}
