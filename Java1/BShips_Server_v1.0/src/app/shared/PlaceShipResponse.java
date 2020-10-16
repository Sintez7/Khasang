package app.shared;

public class PlaceShipResponse extends DataPackage {

    private final boolean accepted;

    public PlaceShipResponse(boolean accepted) {
        super(DataPackage.PLACE_SHIP_RESPONSE);
        this.accepted = accepted;
    }

    public boolean getResponse() {
        return accepted;
    }
}
