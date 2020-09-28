package ex1;

import java.io.Serializable;

public class TextMessage extends Message implements Serializable {

    private String message;

    public TextMessage(String message) {
        type = 2;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
