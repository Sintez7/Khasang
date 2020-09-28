package ex1;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClientHandler extends Thread{

    private Socket client;

    private OutConHandler out;
    private InConHandler in;

    List<Object> list = Collections.synchronizedList(new ArrayList<>());

    public ClientHandler(Socket socket) {
        System.err.println("ClientHandler init");
        client = socket;
        out = new OutConHandler(client);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        in = new InConHandler(client, this);
        out.start();
        in.start();
    }

    @Override
    public void run() {
        System.err.println("ClientHandler started");
        while (true) {
            System.err.println("ClientHandler: new iteration");
            if (list.size() > 0) {
                System.err.println("ClientHandler: list size > 0");
                for (Object o : list) {
                    Message temp = (Message) o;
                    switch (temp.getType()) {
                        case 0 -> handleNull(temp);
                        case 1 -> handleObj(temp);
                        case 2 -> handleTextMessage(temp);
                        case 3 -> handleObjMessage(temp);
                        default -> System.err.println("Unknown Message type");
                    }

                }
            }
            list.clear();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleNull(Message temp) {
        System.err.println("ClientHandler: handleNull method");
        System.err.println("passed null Message Object");
    }

    private void handleObj(Message temp) {
        System.err.println("ClientHandler: handleObj method");
        System.err.println("passed simple Object Message");
    }

    private void handleTextMessage(Message o) {
        System.err.println("ClientHandler: handleTextMessage method");
        TextMessage temp = (TextMessage) o;
        System.err.println("TextMessage content: " + temp.getMessage());
    }

    private void handleObjMessage(Message temp) {
        System.err.println("ClientHandler: handleObjMessage method");
        ObjMessage message = (ObjMessage) temp;
        int[] m = message.getMessage().getM();
        for (int j : m) {
            System.err.println("message massive, " + j);
        }
    }

    public void addMessage (Object message) {
        System.err.println("ClientHandler: adding message, Thread writing: " + Thread.currentThread());
        list.add(message);
    }

    private static class OutConHandler extends Thread {

        private final Socket client;

        public OutConHandler(Socket client) {
//            this.setDaemon(true);
            this.client = client;
        }

        @Override
        public void run() {
            try (ObjectOutputStream oOut = new ObjectOutputStream(new BufferedOutputStream(client.getOutputStream()))){
                oOut.flush();
                System.err.println("OutHandler created and flushed");
                while (true) {
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static class InConHandler extends Thread {

        private final Socket client;
        private final ClientHandler handler;

        public InConHandler(Socket client, ClientHandler clientHandler) {
            this.client = client;
            handler = clientHandler;
        }

        @Override
        public void run() {
            try (ObjectInputStream oIn = new ObjectInputStream(new BufferedInputStream(client.getInputStream()))){
                System.err.println("in created");
                while (true) {
                    System.err.println("inHandler: starting new iteration");
                    try {
                        System.err.println("inHandler: trying to read from in");
                        Object o = oIn.readObject();
                        System.err.println("inHandler: in read");
                        handler.addMessage(o);
                        System.err.println("inHandler: added message to list");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
