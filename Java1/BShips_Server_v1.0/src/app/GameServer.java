// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++ and C#: http://www.viva64.com
package app;

import app.chatFilterModule.ChatFilterModule;
import app.shared.*;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class GameServer extends Thread {
    private static int id;

    private volatile Player player1;
    private volatile Player player2;

    private volatile boolean player1Ready = false;
    private volatile boolean player2Ready = false;

    private volatile boolean player1Rematch = false;
    private volatile boolean player1RematchVoted = false;
    private volatile boolean player2Rematch = false;
    private volatile boolean player2RematchVoted = false;

    private final List<Player> spectators = new ArrayList<>();

    private final Game game;

    private final ChatFilterModule cfm = new ChatFilterModule();

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
        player1.setCurrentGameServer(this);
        player2.setCurrentGameServer(this);
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
        // запускаем саму игру
        game.start();

        // Пока сервер игры используется - он должен работать
        // Если игроки отказались от рематча - он должен быть закрыт и уничтожен
        while (playable) {
            synchronized (game.REMATCH_MONITOR) {
                try {
                    // Ждём пока не будет принято решение о рематче
                    game.REMATCH_MONITOR.wait();
                    // после нотификации, у нас гарантированно будет решение о рематче
                    // если положительно - подготовить рематч,
                    // сбросить переменные готовности и голосования о рематче
                    if (game.rematch) {
                        game.prepareRematch();
                        player1Ready = false;
                        player2Ready = false;
                        player1Rematch = false;
                        player1RematchVoted = false;
                        player2Rematch = false;
                        player2RematchVoted = false;
                    } else {
                        // в ином случае - игра считается завершённой, а потому нужды в сервере более нет,
                        // и его работа прекращается
                        playable = false;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean handlePlaceShip(Player player, PlaceShip ship) {
        System.err.println("handlePlaceShip ing GameServer invoked");
        return game.handlePlaceShip(player, ship);
    }

    public void handleHit(Player player, int x, int y) {
        game.handleHit(player, x, y);
        game.updateClients();
    }

    public void playerReady(Player player) {
        if (player1.equals(player)) {
            player1Ready = true;
        } else {
            if (player2.equals(player)) {
                player2Ready = true;
            }
        }

        if (player1Ready && player2Ready) {
            synchronized (game.PLAYERS_READY) {
                game.PLAYERS_READY.notifyAll();
            }
        }
    }

    public void handleRematchDecision(Player player, RematchDecision dec) {
        if (player1.equals(player)) {
            player1Rematch = dec.getDecision();
            player1RematchVoted = true;
        } else {
            if (player2.equals(player)) {
                player2Rematch = dec.getDecision();
                player2RematchVoted = true;
            }
        }

        if (player1RematchVoted && player2RematchVoted) {
            if (player1Rematch && player2Rematch) {
                game.rematch = player1Rematch & player2Rematch;
                synchronized (game.REMATCH_DECISION_MONITOR) {
                    game.setPositiveRematch();
                    game.REMATCH_DECISION_MONITOR.notifyAll();
                }
            } else {
                // один из игроков отказался от рематча
                game.setNegativeRematch();
                synchronized (game.REMATCH_DECISION_MONITOR) {
                    game.REMATCH_DECISION_MONITOR.notifyAll();
                }
                try {
                    player1.sendData(new RematchDenied());
                    player2.sendData(new RematchDenied());
                } catch (SocketException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void handleChatMessage(Player thisPlayer, ChatMessage in) {
        System.err.println("handling chatMessage");
        String temp = cfm.clearMessage(in.getMessage());

        try {
            player1.sendData(new ChatMessage(thisPlayer.getName(), temp));
            player2.sendData(new ChatMessage(thisPlayer.getName(), temp));
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void handlePlayerConnectionLost(Player player) {
        game.handlePlayerConnectionLost(player);
        if (player.equals(player1)) {
            player1Rematch = false;
            player1RematchVoted = true;
        }
        if (player.equals(player2)) {
            player2Rematch = false;
            player2RematchVoted = true;
        }
    }
}
