public class LogClient {

    public void sendLog(String text, FutureCallback callback) {
        System.out.println(text);

        if (Math.random() > 0.5) {
            callback.completed();
        } else {
            callback.failed();
        }
    }
}
