public class TestServer2 extends Thread {

    public TestServer2(TestServer1 s1) {
        System.out.println("s1.executing from s2");
        s1.execute();
        s1.execute();
        s1.execute();
        s1.execute();
        s1.execute();
    }
}
