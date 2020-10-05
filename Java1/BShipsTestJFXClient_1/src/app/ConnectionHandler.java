package app;

import app.shared.LobbiesDataPackage;
import app.shared.LobbyData;
import javafx.application.Platform;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ConnectionHandler {

    private Socket socket;

    private InConnectionHandler inHandler;
    private OutConnectHandler outHandler;

    private volatile LobbiesScreenController lsController;

    public ConnectionHandler(LobbiesScreenController lsController) {
        if (lsController == null) {
            System.err.println("lsController null in ConnectionHandler");
        }
        this.lsController = lsController;
        try {
            socket = new Socket("localhost", 2111);
            OutConnectHandler outCH = new OutConnectHandler(socket);
            outCH.setDaemon(true);
            outCH.start();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            InConnectionHandler inCH = new InConnectionHandler(socket, this.lsController);
            inCH.setDaemon(true);
            inCH.start();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.err.println("OutCH state: " + outCH.getState());
            System.err.println("InCH state : " + inCH.getState());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class OutConnectHandler extends Thread {
        private Socket socket;
        private ObjectOutputStream out;

        public OutConnectHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                out = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static class InConnectionHandler extends Thread{
        private Socket socket;

        ObjectInputStream in;
        private volatile LobbiesScreenController lsController;

        public InConnectionHandler(Socket socket, LobbiesScreenController lsController) {
            this.socket = socket;
            this.lsController = lsController;
        }

        @Override
        public void run() {
            try {
                in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
                while(true) {
                    try {
//                        LobbiesPackage lp = (LobbiesDataPackage) in.readObject();
//                        controller.refreshLobbiesList(lp);
                        Object input = in.readObject();
                        System.err.println("echo: " + input);
//                        lsController.addLobbies(((LobbiesDataPackage)input).getList());
                        List<LobbyData> dPackage = ((LobbiesDataPackage)input).getList();
                        System.err.println(Thread.currentThread());
                        Platform.runLater(() -> {
                            System.err.println(Thread.currentThread());
                            lsController.addLobbies(dPackage);
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
