package ex1;

import java.io.IOException;
import java.net.ServerSocket;

public class Server extends Thread {

    @Override
    public void run() {
        try {
            ServerSocket sSocket = new ServerSocket(2222);
            while (true) {
                System.err.println("new connection waiting");
                new ClientHandler(sSocket.accept()).start();
                System.err.println("accepted");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
