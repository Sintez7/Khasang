public class StackException extends Exception{

    private final String reason;

    public StackException(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "StackException reason: " + reason;
    }
}
