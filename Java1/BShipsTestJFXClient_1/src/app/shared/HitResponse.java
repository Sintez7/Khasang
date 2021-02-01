package app.shared;

public class HitResponse extends DataPackage {

    public static final int HIT = 1;
    public static final int MISS = 2;
    public static final int ALREADY_HIT = 3;
    public static final int HIT_SHIP = 4;
    public static final int CANT_SHOOT = 5;
    public static final int SUNK_SHIP = 6;

    private final int responseType;

    public HitResponse(int responseType) {
        super(DataPackage.HIT_RESPONSE);
        this.responseType = responseType;
    }

    public int getResponseType() {
        return responseType;
    }
}
