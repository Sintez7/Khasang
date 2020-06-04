public class Cat implements Runnable {

    public final Object monitor = new Object();

    public boolean voice() throws MyException{
        for (int i = 0; i < 11; i++) {
            System.err.println("MEOW " + i);
            if(Math.random() > 0.95) {
                Thread.yield();
                throw new MyException();
            }
        }
        return true;
    }

    @Override
    public void run(){
        synchronized (monitor) {
            try {
                monitor.wait();
            } catch (InterruptedException e) {
                System.err.println("Cat awakened! after exception");
                e.printStackTrace();
            }
        }
        System.err.println("Cat awakened! after try/catch");
    }
}
