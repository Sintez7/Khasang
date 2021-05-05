package app;

public class SerrOut implements Output {
    @Override
    public void send(String text) {
        System.err.println(text);
    }
}
