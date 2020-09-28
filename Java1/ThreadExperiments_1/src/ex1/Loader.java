package ex1;

public class Loader {

    public static void main(String[] args) {
        Server server = new Server();
        Client client = new Client();
//        server.setDaemon(true);
//        client.setDaemon(true);
        server.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        client.start();
    }
}
