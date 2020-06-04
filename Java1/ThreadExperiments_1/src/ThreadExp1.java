public class ThreadExp1 {

    static Cat cat = new Cat();
    static Thread catThread = new Thread(cat);


    static Dog dog = new Dog(cat);
    static Thread dogThread = new Thread(dog);

    static volatile Object key = new Object();

    public static void showStatus() {

        System.err.println(catThread.getState());
        System.err.println(dogThread.getState());
    }

    public void start() {
        Object myMonitor = new Object();

        catThread.start();
        dogThread.start();

        try {
            synchronized (myMonitor){
                myMonitor.wait(3000);
            }
        } catch (InterruptedException e) {

        }

        showStatus();

        try {
            synchronized (myMonitor){
                myMonitor.wait(1500);
            }
        } catch (InterruptedException e) {

        }

        showStatus();
    }
}


/*
 Object monitor = new Object();
 new Thread(() -> {
 for (int i = 0; i < 10; i++) {
 try {
 System.err.println("iteration: " + i);
 Thread.sleep(100);
 } catch (InterruptedException e) {
 synchronized (monitor) {
 monitor.notifyAll();
 }
 }
 }
 }).start();

 new Thread (() -> {
 }).start();
 */