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
    private static int id = 0;

    private final Socket client;
    private final SynchronousQueue<DataPackage> inputQueue = new SynchronousQueue<>(true);
    private final Player thisPlayer;
    private final LobbyServer lobbyServer;
    private final InExecutor inExecutor;

    // в этом потоке пишем ОТСЮДА ТУДА (от сервера на клиент)
    private ObjectOutputStream out = null;
    // в этом потоке читаем ОТТУДА СЮДА (от клиента на сервер)
    private ObjectInputStream in;

    private LobbyRoom currentRoom;
    private GameServer currentGameServer;
    private boolean connectionAlive = false;

    public ClientHandler(Socket client, LobbyServer lobbyServer) {
        setName("ClientHandler " + id++);
        this.client = client;
        Player player = new ActualPlayer(this);
        thisPlayer = player;
        this.lobbyServer = lobbyServer;
        lobbyServer.addPlayer(player);
        inExecutor = new InExecutor();
        inExecutor.setDaemon(true);
        inExecutor.setName("InExecutor " + id);
        inExecutor.start();
        connectionAlive = true;
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
                while (connectionAlive) {
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
                connectionAlive = false;
                inExecutor.interrupt();
            }
        } catch (IOException e) {
            e.printStackTrace();
            try { // закрываем выходящий поток
                if (out != null) {
                    out.close();
                } // закрываем входящий поток
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

    private void transferPlayerToRoom(int lobbyId) {
        lobbyServer.movePlayerToLobbyRoom(thisPlayer, lobbyId);
    }

    private void gameStart() {
        currentGameServer = currentRoom.startGame();
        System.err.println(currentGameServer);
    }

    private void playerMove() {
//        currentGameServer.playerMove();
    }

    private void handlePlaceShip(PlaceShip ship) {
        System.err.println(currentGameServer + " player " + thisPlayer);
        try {
            sendData(new PlaceShipResponse(currentGameServer.handlePlaceShip(thisPlayer, ship)));
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    private void handleHit(Hit in) {
        currentGameServer.handleHit(thisPlayer, in.getX(), in.getY());
    }

    public void setCurrentGameServer(GameServer gameServer) {
        currentGameServer = gameServer;
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
                        case DataPackage.PLAYER_NAME -> setPlayerName(((PlayerName) in).getName());
                        case DataPackage.LOBBY_CHOICE -> transferPlayerToRoom(((LobbyChoice) in).lobbyId);
                        case DataPackage.CREATE_LOBBY -> createLobby();
                        case DataPackage.LEAVE_ROOM -> returnPlayerToLobbyServer();
                        case DataPackage.GAME_START -> gameStart();
                        case DataPackage.PLACE_SHIP -> handlePlaceShip((PlaceShip) in);
                        case DataPackage.READY_TO_GAME_START -> handleReady();
                        case DataPackage.HIT -> handleHit((Hit) in); //TODO obrabotat' package
                        case DataPackage.REMATCH_DECISION -> handleRematchDecision();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
    }

    private void handleRematchDecision() {
        currentGameServer.handleRematchDecision(thisPlayer);
    }

    private void handleReady() {
        currentGameServer.playerReady(thisPlayer);
    }

    private void returnPlayerToLobbyServer() {
        currentRoom.returnPlayerToLobbyServer(thisPlayer);
        lobbyServer.addPlayer(thisPlayer);
        try {
            sendData(new ReturnToLobby());
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    private void createLobby() {
        Lobby temp = lobbyServer.newLobby("");
        transferPlayerToRoom(temp.getId());
        try {
            sendData(new EnterRoom());
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    private void setPlayerName(String name) {
        thisPlayer.setName(name);
    }
}
