package app;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {

         // LobbyServer будет первым принимать клиентов
        LobbyServer lobbyServer = new LobbyServer();
        lobbyServer.setDaemon(true);
        lobbyServer.start();

        try (ServerSocket server1 = new ServerSocket(2111)){
            System.err.println("server started");
            while (true) {
                System.err.println("waiting for connection");
                Socket client = server1.accept();
                System.err.println("accepted");
                System.err.println("connected address" + client.getInetAddress() + " port: " + client.getLocalPort());
                new ClientHandler(client, lobbyServer).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
