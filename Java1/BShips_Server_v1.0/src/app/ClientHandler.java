package app;

import app.shared.DataPackage;
import app.shared.LobbyChoice;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;
import java.util.concurrent.SynchronousQueue;

public class ClientHandler extends Thread {

    private final Object IN_MONITOR = new Object();

    // сокет как агрегат всех точек коннекта
    private final Socket client;

    // в этом потоке пишем ОТСЮДА ТУДА (от сервера на клиент)
    private ObjectOutputStream out = null;
    // в этом потоке читаем ОТТУДА СЮДА (от клиента на сервер)
    private ObjectInputStream in;

    private List<String> lines = Collections.synchronizedList(new ArrayList<>());

    private final SynchronousQueue<DataPackage> inputQueue = new SynchronousQueue<>(true);

    private final Player thisPlayer;
    private final LobbyServer lobbyServer;

    public ClientHandler(Socket client, LobbyServer lobbyServer) {
        this.client = client;
        Player player = new ActualPlayer(this);
        thisPlayer = player;
        this.lobbyServer = lobbyServer;
        lobbyServer.addPlayer(player);
    }

    // Здесь мы ПРИНИМАЕМ даные, если метод выкинул ошибку - клиент отвалился
    @Override
    public void run() {
        try {
//            Test11 test = new Test11();
            // инициализируем всё это непотребство
            out = new ObjectOutputStream(new BufferedOutputStream(client.getOutputStream()));
            out.flush();
//            in = new ObjectInputStream(new BufferedInputStream(client.getInputStream()));
            InConnectionHandler in = new InConnectionHandler(client);

        } catch (IOException e) { // чекает в ран-тайме на коннект
            e.printStackTrace();
            try { // закрываем выходящий поток, зачем - а хер его знает...
                // надо ли закрывать in? надо
                if (out != null) {
                    out.close();
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

    public void sendData(DataPackage dataPackage) throws SocketException {
        try {
            out.writeObject(dataPackage);
            out.flush();
        } catch (SocketException e1) {
            throw e1;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void transferPlayerToLobby(int lobbyId) {
        lobbyServer.movePlayerToLobbyRoom(thisPlayer, lobbyId);
    }

    private class InExecutor extends Thread {
        @Override
        public void run() {
            DataPackage in = null;
            try {
                in = inputQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            switch (in.getId()) {
                case 11 -> transferPlayerToLobby(((LobbyChoice)in).lobbyId);
            }
        }
    }

    private class InConnectionHandler extends Thread{

        Socket client;
        public InConnectionHandler(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {
            try {
                in = new ObjectInputStream(new BufferedInputStream(client.getInputStream()));
                while (true) {
                    try {
                        Object input = in.readObject();
                        inputQueue.offer((DataPackage)input);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                        // Клиент ОТВАЛИЛСЯ
                    } finally {
                        if (in != null) {
                            in.close();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
