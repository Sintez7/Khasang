// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++ and C#: http://www.viva64.com
package app.shared;

public class HitResponse extends DataPackage {

    public static final int HIT = 1;
    public static final int MISS = 2;
    public static final int ALREADY_HIT = 3;
    public static final int HIT_SHIP = 4;
    public static final int CANT_SHOOT = 5;

    private final int responseType;

    public HitResponse(int responseType) {
        super(DataPackage.HIT_RESPONSE);
        this.responseType = responseType;
    }

    public int getResponseType() {
        return responseType;
    }
}
