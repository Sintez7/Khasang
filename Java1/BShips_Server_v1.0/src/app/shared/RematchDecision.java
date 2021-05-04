// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++ and C#: http://www.viva64.com
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
