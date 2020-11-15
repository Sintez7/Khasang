package app;

import app.shared.DataPackage;
import app.shared.GameStart;
import app.shared.PlaceShip;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

public class GameServer extends Thread {
    private static int id;

    private volatile Player player1;
    private volatile Player player2;

    private volatile boolean player1Ready = false;
    private volatile boolean player2Ready = false;

//    private Field player1Field;
//    private Field player2Field;

    private final List<Player> spectators = new ArrayList<>();

    private final Game game;

    public GameServer(Player player1, Player player2, List<Player> spectators) {
        setName("GameServer " + id++);
        this.player1 = player1;
        this.player2 = player2;
        this.spectators.addAll(spectators);
        game = new Game(player1, player2);
    }

    public void setPlayer1(Player player) {
        player1 = player;
    }

    public void setPlayer2(Player player) {
        player2 = player;
    }

    public GameServer startGame() {
        this.start();
        return this;
    }

    @Override
    public void run() {
        boolean playable = true;
        // Оповещаем всех о том что игра началась
        DataPackage startPackage = new GameStart();
        try {
            player1.sendData(startPackage);
            player2.sendData(startPackage);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        for (Player spectator : spectators) {
            try {
                spectator.sendData(startPackage);
            } catch (SocketException e) {
                // spectator отвалился
                e.printStackTrace();
            }
        }

        // Пока сервер игры используется - он должен работать
        // Если игроки отказались от рематча - он должен быть закрыт и уничтожен
        while (playable) {
            synchronized (game.REMATCH_MONITOR) {
                try {
                    // Ждём пока не будет принято решение о рематче
                    game.REMATCH_MONITOR.wait();
                    // после нотификации, у нас гарантированно будет решение о рематче
                    if (game.rematch) {
                        game.prepareRematch();
                    } else {
                        playable = false;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean handlePlaceShip(Player player, PlaceShip ship) {
        return game.handlePlaceShip(player, ship);
    }

    public void handleHit(Player player, int x, int y) {
        game.handleHit(player, x, y);
        game.updateClients();
    }

    public void playerReady(Player thisPlayer) {
        if (player1.equals(thisPlayer)) {
            player1Ready = true;
        } else {
            if (player2.equals(thisPlayer)) {
                player2Ready = true;
            }
        }

        if (player1Ready && player2Ready) {
            synchronized (game.PLAYERS_READY) {
                game.PLAYERS_READY.notifyAll();
            }
        }
    }
}
