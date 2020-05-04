public class Loader {

    public static void main(String[] args) {
        FutureCallbackClass callback = new FutureCallbackClass();
        LogClient log = new LogClient();
        log.sendLog("qwe", callback);

        log.sendLog("asd", new FutureCallback() {
            @Override
            public void completed() {
                System.out.println("Anonymous Ok");
            }

            @Override
            public void failed() {
                System.out.println("Anonymous Error");
            }
        });
    }
}
