package app;

public class TestServer1 extends Thread {

    TestServer1StateMachine sm = new TestServer1StateMachine();

    @Override
    public void run() {
        System.err.println("s1 start sleep");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.err.println("s1 sleep ended");
        sm.execute();
        sm.execute();
        sm.execute();
    }

    public void execute() {
        sm.execute();
    }
}
