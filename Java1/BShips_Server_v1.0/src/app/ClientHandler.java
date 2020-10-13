package app;

import app.shared.DataPackage;
import app.shared.LobbyChoice;

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
    // сокет как агрегат всех точек коннекта
    private final Socket client;
    private final SynchronousQueue<DataPackage> inputQueue = new SynchronousQueue<>(true);
    private final Player thisPlayer;
    private final LobbyServer lobbyServer;

    // в этом потоке пишем ОТСЮДА ТУДА (от сервера на клиент)
    private ObjectOutputStream out = null;
    // в этом потоке читаем ОТТУДА СЮДА (от клиента на сервер)
    private ObjectInputStream in;

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
            }finally {
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

    private void transferPlayerToLobby(int lobbyId) {
        lobbyServer.movePlayerToLobbyRoom(thisPlayer, lobbyId);
    }

    private class InExecutor extends Thread {

        /*
         * Берёт на себя нагрузку в виде внутренней обработки входящих пакетов.
         * Здесь происходит определение типа пакета и вызов
         * следущих методов цепи обработки входящих пакетов
         */

        @Override
        public void run() {
            DataPackage in = null;
            try {
                in = inputQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            switch (in.getId()) {
                case 11 -> transferPlayerToLobby(((LobbyChoice) in).lobbyId);
            }
        }
    }
}
