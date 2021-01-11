package app;

import app.shared.*;
import javafx.application.Platform;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class ConnectionHandler extends Thread {

//    private final SynchronousQueue<DataPackage> inQueue = new SynchronousQueue<>();
    private final BlockingQueue<DataPackage> inQueue = new LinkedBlockingQueue<DataPackage>();
    private final InExecutor inExecutor = new InExecutor(inQueue);
    private Socket socket;

    private ObjectInputStream in;
    private ObjectOutputStream out;

    private volatile LobbiesScreenController lsController;

    public static Main main;
    public static volatile boolean notClosed = true;

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
            in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            try {
                synchronized (main.LOADED_MONITOR) {
                    main.LOADED_MONITOR.notifyAll();
                }
                while (notClosed) {
                    Object input = in.readObject();
                    System.err.println("echo: " + input);
                    inQueue.offer((DataPackage)input);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("server otvalilsya");
                notClosed = false;
                inExecutor.interrupt();
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

        private final BlockingQueue<DataPackage> inQueue;

        LobbiesScreenController lsController;

        public InExecutor(BlockingQueue<DataPackage> inQueue) {
            setName("InExecutor Thread");
            setDaemon(true);
            this.inQueue = inQueue;
        }

        public void setLsController(LobbiesScreenController lsController) {
            this.lsController = lsController;
        }

        @Override
        public void run() {
            while (notClosed) {
                try {
                    DataPackage temp = inQueue.take();
                    switch (temp.getId()) {
                        case DataPackage.LOBBY_PACKAGE -> Platform.runLater(() -> main.handleLobbiesPackage((LobbiesDataPackage)temp));
                        case DataPackage.ROOM -> Platform.runLater(() -> main.handleRoomPackage((LobbyRoomData)temp));
                        case DataPackage.RETURN_TO_LOBBY -> Platform.runLater(() -> main.loadLobby());
                        case DataPackage.ENTER_ROOM -> Platform.runLater(() -> main.loadRoom());
                        case DataPackage.GAME_START -> Platform.runLater(() -> main.handleGameStart());
                        case DataPackage.PLACE_SHIP_RESPONSE -> Platform.runLater(() ->
                                main.handlePlaceShipResponse((PlaceShipResponse) temp));
                        case DataPackage.HIT_RESPONSE -> Platform.runLater(() -> main.handleHitResponse((HitResponse)temp));
                        case DataPackage.TURN_UPDATE -> Platform.runLater(() -> main.handleTurnUpdate((TurnUpdate)temp));
                        case DataPackage.PLAYER_INFO -> Platform.runLater(() -> main.handlePlayerInfo((PlayerInfo) temp));
                        case DataPackage.PLAYER_WON -> Platform.runLater(() -> main.handlePlayerWon((PlayerWon) temp));
                        case DataPackage.REMATCH_OFFER -> Platform.runLater(() -> main.handleRematchOffer());
                        case DataPackage.REMATCH_SIGNAL -> Platform.runLater(() -> main.handleRematch());
                        case DataPackage.BATTLE_START -> Platform.runLater(() -> main.handleBattleStart());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
