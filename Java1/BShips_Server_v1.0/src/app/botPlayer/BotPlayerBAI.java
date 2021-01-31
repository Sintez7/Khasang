package app.botPlayer;

import app.Point;
import app.shared.Hit;
import app.shared.HitResponse;
import app.shared.TurnUpdate;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class BotPlayerBAI {
    /*
     * Battle Artificial Intelligence
     * ИИ отвечающий за ведение боя
     */
    private final BotPlayer owner;
    private final Random random = new Random();
    private HashMap<Point, Boolean> availableCells = new HashMap<>();
    private ArrayList<Point> availablePoints = new ArrayList<>(100);

    private int curX;
    private int curY;

    private int last1X;
    private int last1Y;
    private int last2X;
    private int last2Y;

    private Point last1 = null;
    private Point last2 = null;
    private Point last3 = null;
    private Point last4 = null;

    private boolean hit = false;

    public BotPlayerBAI(BotPlayer owner) {
        this.owner = owner;
        for (int i = 0; i < 10; i++) {
             for (int j = 0; j < 10; j++) {
                 availableCells.put(new Point(i, j), true);
             }
        }
    }

    public void shoot() {
//        int x = random.nextInt(10);
//        int y = random.nextInt(10);
        if (hit) {
            nextShot();
        } else {
            randomShot();
        }
        try {
            owner.sendData(new Hit(curX, curY));
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void handleHitResponse(HitResponse in) {
        if (in.getResponseType() == 1) {
            hit = true;
        } else {
            hit = false;
            last1 = null;
        }
    }

    private void nextShot() {
//        Point firstHit = last1;
        if (last2 != null) {

        }
    }

    private void randomShot() {
        int aimIndex = random.nextInt(availablePoints.size());
        Point cur = availablePoints.get(aimIndex);
        try {
            owner.sendData(new Hit(cur.x, cur.y));
        } catch (SocketException e) {
            e.printStackTrace();
        }

        last1 = cur;
    }

    public void handleTurnUpdate(TurnUpdate in) {
        int[][] temp = in.getOpponentField();
        availablePoints.clear();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (temp[i][j] == 0) {
                    availablePoints.add(new Point(j, i));
                }
            }
        }
    }
}
