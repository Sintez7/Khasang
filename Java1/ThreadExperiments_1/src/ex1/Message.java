package ex1;

import java.io.Serializable;

public abstract class Message implements Serializable {
    private Object message;
    protected int type;

    public Message() {
        this(null);
    }

    public Message (Object message) {
        if (message == null) {
            type = 0;
            this.message = null;
        } else {
            type = 1;
            this.message = message;
        }
    }

    public Object getMessage() {
        if (message == null) {
            System.err.println("message is null");
            return null;
        } else {
            return message;
        }
    }

    public int getType() {
        return type;
    }
}
