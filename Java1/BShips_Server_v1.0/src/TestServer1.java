public class TestServer1 extends Thread {

    TestServer1StateMachine sm = new TestServer1StateMachine();

    @Override
    public void run() {
    }

    public void execute() {
        sm.execute();
    }
}
