package app;

import app.shared.*;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.SynchronousQueue;

/*
 * ClientHandler предназначен для обработки входящего подключения как клиента
 * Наследует Thread, чтобы снять нагрузку с основного класса,
 * который ожидает подключения, тем самым обеспечивая некоторе быстродействие
 */

public class ClientHandler extends Thread {
    private final Socket client;
    private final SynchronousQueue<DataPackage> inputQueue = new SynchronousQueue<>(true);
    private final Player thisPlayer;
    private final LobbyServer lobbyServer;

    // в этом потоке пишем ОТСЮДА ТУДА (от сервера на клиент)
    private ObjectOutputStream out = null;
    // в этом потоке читаем ОТТУДА СЮДА (от клиента на сервер)
    private ObjectInputStream in;

    private LobbyRoom currentRoom;
    private GameServer currentGameServer;

    public ClientHandler(Socket client, LobbyServer lobbyServer) {
        this.client = client;
        Player player = new ActualPlayer(this);
        thisPlayer = player;
        this.lobbyServer = lobbyServer;
        lobbyServer.addPlayer(player);
        new InExecutor().start();
    }

    // если метод выкинул ошибку - клиент отвалился
    @Override
    public void run() {
        try {
            out = new ObjectOutputStream(new BufferedOutputStream(client.getOutputStream()));
            out.flush();
            in = new ObjectInputStream(new BufferedInputStream(client.getInputStream()));

            /*
             * Обработка входящего пакета может быть довольно долгой.
             * Чтобы принимать пакеты от клиента как можно чаще,
             * делегируем обработку классу InExecutor через синхронизованную очередь
             */
            try {
                while (true) {
                    Object input = in.readObject();
                    inputQueue.offer((DataPackage) input);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SocketException e1) {
                // Клиент ОТВАЛИЛСЯ
                System.err.println(client.toString() + " connection lost");
                // Завершаем цикл чтения
//                    break;
            } finally {
                if (in != null) {
                    in.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            try { // закрываем выходящий поток
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            try { // закрываем коннект с сокетом, а значит отваливаемся от него
                client.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }

    /*
     * Бросает SocketException, чтобы класс использовавший метод,
     * мог определить что клиент отвалился, и принять меры на своей стороне
     */
    public void sendData(DataPackage dataPackage) throws SocketException {
        try {
            out.writeObject(dataPackage);
            out.flush();
        } catch (SocketException e1) {
            throw e1;
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("IOEx e, klient otvalilsya");
        }
    }

    public LobbyRoom getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(LobbyRoom currentRoom) {
        this.currentRoom = currentRoom;
    }

    private void transferPlayerToLobby(int lobbyId) {
        lobbyServer.movePlayerToLobbyRoom(thisPlayer, lobbyId);
    }

    private void gameStart() {
        currentGameServer = currentRoom.startGame();
    }

    private void playerMove() {
//        currentGameServer.playerMove();
    }

    private void handlePlaceShip(PlaceShip ship) {
        try {
            sendData(new PlaceShipResponse(currentGameServer.handlePlaceShip(thisPlayer, ship)));
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    private void handleHit(Hit in) {
        currentGameServer.handleHit(thisPlayer, in.getX(), in.getY());
    }

    private class InExecutor extends Thread {

        /*
         * Берёт на себя нагрузку в виде внутренней обработки входящих пакетов.
         * Здесь происходит определение типа пакета и вызов
         * следущих методов цепи обработки входящих пакетов
         */

        @Override
        public void run() {
            DataPackage in;
            while (true) {
                try {
                    in = inputQueue.take();
                    switch (in.getId()) {
                        case DataPackage.LOBBY_CHOICE -> transferPlayerToLobby(((LobbyChoice) in).lobbyId);
                        case DataPackage.GAME_START -> gameStart();
                        case DataPackage.PLACE_SHIP -> handlePlaceShip((PlaceShip) in);
                        case DataPackage.HIT -> handleHit((Hit)in); //TODO obrabotat' package

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
