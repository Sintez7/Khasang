package app;

import app.shared.DataPackage;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ClientHandler extends Thread {

    // сокет как агрегат всех точек коннекта
    private final Socket client;

    // в этом потоке пишем ОТСЮДА ТУДА (от сервера на клиент)
    private ObjectOutputStream out = null;
    // в этом потоке читаем ОТТУДА СЮДА (от клиента на сервер)
    private ObjectInputStream in;

    private List<String> lines = Collections.synchronizedList(new ArrayList<>());

    public ClientHandler(Socket client, LobbyServer lobbyServer) {
        this.client = client;



        lobbyServer.addPlayer(new ActualPlayer(this));
    }

    // Здесь мы ПРИНИМАЕМ даные, если метод выкинул ошибку - клиент отвалился
    @Override
    public void run() {
        try {
            // инициализируем всё это непотребство
            out = new ObjectOutputStream(new BufferedOutputStream(client.getOutputStream()));
            out.flush();
            in = new ObjectInputStream(new BufferedInputStream(client.getInputStream()));
            try {
                while (true) { // TODO по хорошему, должен быть флаг
                    String line = in.readUTF();
                    lines.add(line);
                    // save line or continue work
                }
            } catch (IOException e) {
                // Клиент ОТВАЛИЛСЯ

            } finally {
                // вот тут описываются действия если клиент отвалился
            }
        } catch (IOException e) { // чекает в ран-тайме на коннект
            e.printStackTrace();
            try { // закрываем выходящий поток, зачем - а хер его знает...
                // надо ли закрывать in? надо
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

    public String getLine() {
        String temp = "";
        Iterator<String> it = lines.iterator();
        if (it.hasNext()) {
            temp = it.next();
            it.remove();
        }
        return temp;
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

    public void sendData(String s) {
        try {
            out.writeObject(s);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
