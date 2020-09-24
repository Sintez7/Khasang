package app;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {
//        app.Field field = new app.Field();
//        field.printField();
//        System.out.println(field.placeShip(1,1, new app.Ship(1, app.Ship.UP)));
//        System.out.println(field.placeShip(1,1, new app.Ship(1, app.Ship.UP)));
//        field.printField();
//        System.out.println(field.placeShip(10,10, new app.Ship(1, app.Ship.LEFT)));
//        field.printField();
//        System.out.println(field.placeShip(2,2, new app.Ship(1, app.Ship.UP)));
//        field.printField();
//        System.out.println(field.placeShip(1,2, new app.Ship(1, app.Ship.UP)));
//        field.printField();
//        System.out.println(field.placeShip(2,1, new app.Ship(1, app.Ship.UP)));
//        field.printField();
//        System.out.println(field.placeShip(2,2, new app.Ship(1)));
//        field.printField();
//        System.out.println(field.placeShip(2,2, new app.Ship(1)));
//        field.printField();
//
//        System.out.println(field.placeShip(5,5, new app.Ship(4, app.Ship.UP)));
//        field.printField();
//        System.out.println(field.placeShip(8,8, new app.Ship(4, app.Ship.UP)));
//        field.printField();
//        System.out.println(field.placeShip(3,7, new app.Ship(4, app.Ship.DOWN)));
//        field.printField();
//        System.out.println(field.placeShip(1,8, new app.Ship(4, app.Ship.RIGHT)));
//        field.printField();

//        app.TestServer1 s1 = new app.TestServer1();
//        s1.start();
//        app.TestServer2 s2 = new app.TestServer2(s1);
//        s2.start();

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
