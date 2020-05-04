public class FutureCallbackClass implements FutureCallback {
    @Override
    public void completed() {
        System.out.println("Ok");
    }

    @Override
    public void failed() {
        System.out.println("Error");
    }
}
