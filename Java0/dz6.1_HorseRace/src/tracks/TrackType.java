package tracks;

public enum TrackType {
    SPRINT("Sprint") {
        @Override
        public Track getInstance() {
            return new Sprint();
        }
    },
    ROUND("Round") {
        @Override
        public Track getInstance() {
            return new Round();
        }
    },
    TIME_ATTACK("Time Attack") {
        @Override
        public Track getInstance() {
            return new TimeAttack();
        }
    };

    private String name;

    TrackType(String name) {
        this.name = name;
    }

    public String getTrackName(){
        return name;
    }

    abstract public Track getInstance();
}
