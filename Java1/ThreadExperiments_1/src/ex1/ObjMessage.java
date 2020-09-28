package ex1;

import java.io.Serializable;

public class ObjMessage extends Message implements Serializable {

    CustomMessageObj m;

    public ObjMessage(CustomMessageObj message) {
        type = 3;
        m = message;
    }

    public CustomMessageObj getMessage() {
        return m;
    }
}
