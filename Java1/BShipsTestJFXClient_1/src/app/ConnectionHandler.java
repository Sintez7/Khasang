package app;

import app.shared.DataPackage;
import app.shared.LobbiesDataPackage;
import app.shared.LobbyData;
import app.shared.LobbyRoomData;
import javafx.application.Platform;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.SynchronousQueue;

public class ConnectionHandler extends Thread {

    private final SynchronousQueue<DataPackage> inQueue = new SynchronousQueue<>();
    private final InExecutor inExecutor = new InExecutor(inQueue);
    private Socket socket;

    private ObjectInputStream in;
    private ObjectOutputStream out;

    private volatile LobbiesScreenController lsController;

    public static Main main;

    public ConnectionHandler(LobbiesScreenController lsController, Main main) {
        this.main = main;
        inExecutor.setLsController(lsController);
        inExecutor.start();
        if (lsController == null) {
            System.err.println("lsController null in ConnectionHandler");
        }
        this.lsController = lsController;
    }

    @Override
    public void run() {
        try {
            socket = new Socket("localhost", 2111);
            out = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            out.flush();

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            try {
                while (true) {
                    Object input = in.readObject();
                    System.err.println("echo: " + input);
                    inQueue.offer((DataPackage)input);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("server otvalilsya");
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendData(DataPackage data) {
        try {
            out.writeObject(data);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class InExecutor extends Thread {

        private final SynchronousQueue<DataPackage> inQueue;

        LobbiesScreenController lsController;

        public InExecutor(SynchronousQueue<DataPackage> inQueue) {
            this.inQueue = inQueue;
        }

        public void setLsController(LobbiesScreenController lsController) {
            this.lsController = lsController;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    DataPackage temp = inQueue.take();
                    switch (temp.getId()) {
                        case 1 -> Platform.runLater(() -> main.handleLobbiesPackage((LobbiesDataPackage)temp));
                        case 12 -> Platform.runLater(() -> main.handleRoomPackage((LobbyRoomData)temp));
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
