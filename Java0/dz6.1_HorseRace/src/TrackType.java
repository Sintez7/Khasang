public enum TrackType {
    SPRINT("Sprint"),
    ROUND("Round"),
    TIME_ATTACK("Time Attack");

    private String name;

    TrackType(String name) {
        this.name = name;
    }

    public String getTrackName(){
        return name;
    }
}
