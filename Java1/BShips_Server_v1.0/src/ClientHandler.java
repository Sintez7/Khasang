import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread implements Player {

    // сокет как агрегат всех точек коннекта
    private final Socket client;

    // в этом потоке читаем ОТТУДА СЮДА (от клиента на сервер)
    private DataInputStream in;
    // в этом потоке пишем ОТСЮДА ТУДА (от сервера на клиент)
    private DataOutputStream out = null;

    public ClientHandler(Socket client) {
        this.client = client;

        try {
            // инициализируем всё это непотребство
            in = new DataInputStream(new BufferedInputStream(client.getInputStream()));
            out = new DataOutputStream(new BufferedOutputStream(client.getOutputStream()));
        } catch (IOException e) { // TODO проверить на отвал клиента в рантайме, ибо хз, чекает ли эта хрень текущее состояние
            e.printStackTrace();
            try { // закрываем выходящий поток, зачем - а хер его знает...
                // надо ли закрывать in?
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

    // Здесь мы ПРИНИМАЕМ даные, если метод выкинул ошибку - клиент отвалился
    @Override
    public void run() {
        try {
            while (true) { // TODO по хорошему, должен быть флаг
                String line = in.readUTF();
                // save line or continue work
            }
        } catch (IOException e) {
            // Клиент ОТВАЛИЛСЯ

        } finally {
            // вот тут описываются действия если клиент отвалился
        }
    }
}
