public class Dog implements Runnable {

    public final Object monitor = new Object();
    Cat cat;
    int counter = 0;

    public Dog(Cat cat) {
        this.cat = cat;
    }

    @Override
    public void run() {
        while(true) {
            try {
                cat.voice();
            } catch (MyException e) {
                e.printStackTrace();

                counter++;
                if (counter >= 1) {
                    ThreadExp1.showStatus();
                }

                try {
                    synchronized (this){
                        this.wait(500);
                    }
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }

                if (counter >= 5) {
//                    System.exit(101);
                    synchronized (cat.monitor){
                        cat.monitor.notifyAll();
                    }
                    break;
                }
            }
            Thread.yield();
        }

//        finally {
//            run2();
//        }
    }

    private void run2() {
        try {
            cat.voice();
        } catch (MyException e) {
            e.printStackTrace();
            counter++;
        }
        finally {
            run();
        }
    }
}
