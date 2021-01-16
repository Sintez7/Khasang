package app.shared;

public class ChatMessage extends DataPackage {

    String name;
    String message;

    public ChatMessage(String name, String message) {
        super(CHAT_MESSAGE_PACKAGE);
        this.name = name;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }
}
