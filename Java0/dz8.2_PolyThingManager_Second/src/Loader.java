import app.AppInterface;
import app.Application;

public class Loader {

    public static void main(String[] args) {
        AppInterface app = new Application();
        app.start();
    }
}
