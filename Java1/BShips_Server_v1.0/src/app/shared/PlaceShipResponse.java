// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++ and C#: http://www.viva64.com
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
