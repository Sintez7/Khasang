package app.shared;

public class RematchDecision extends DataPackage {

    private final boolean decision;

    public RematchDecision(boolean decision) {
        super(DataPackage.REMATCH_DECISION);
        this.decision = decision;
    }

    public boolean getDecision() {
        return decision;
    }
}
