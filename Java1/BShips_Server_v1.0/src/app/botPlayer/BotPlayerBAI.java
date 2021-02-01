package app.botPlayer;

import app.Point;
import app.shared.Hit;
import app.shared.HitResponse;
import app.shared.TurnUpdate;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

public class BotPlayerBAI {
    /*
     * Battle Artificial Intelligence
     * ИИ отвечающий за ведение боя
     */

    private static final Point UP = new Point(0, -1);
    private static final Point RIGHT = new Point(1, 0);
    private static final Point DOWN = new Point(0, 1);
    private static final Point LEFT = new Point(-1, 0);

    private final BotPlayer owner;
    private final Random random = new Random();
//    private HashMap<Point, Boolean> availableCells = new HashMap<>();
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

    private final ArrayList<Point> chainShots = new ArrayList<>();
    private final HashMap<Point, Boolean> chains = new HashMap<>();

    private boolean hit = false;
    private boolean chain = false;

    public BotPlayerBAI(BotPlayer owner) {
        this.owner = owner;
//        for (int i = 0; i < 10; i++) {
//             for (int j = 0; j < 10; j++) {
//                 availableCells.put(new Point(i, j), true);
//             }
//        }
    }

    public void shoot() {
//        int x = random.nextInt(10);
//        int y = random.nextInt(10);
        if (hit || chain) {
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
        if (in.getResponseType() == HitResponse.HIT) {
            hit = true;
            chain = true;
//            chainShots.add(new Point(curX, curY));
            chains.put(new Point(curX, curY), true);
        }

        if (in.getResponseType() == HitResponse.MISS) {
            hit = false;
        }

        if (in.getResponseType() == HitResponse.SUNK_SHIP) {
            chain = false;
//            chainShots.clear();
            chains.clear();
        }
    }

    private void nextShot() {
//        if (last3 != null) {
//            if (checkForAliveShip(last3)) {
//                fourthShot();
//                return;
//            }
//        }
//
//        if (last2 != null) {
//            if (checkForAliveShip(last2)) {
//                thirdShot();
//                return;
//            }
//        }
//
//        if (checkForAliveShip(last1)) {
//            secondShot();
//            return;
//        }
//        randomShot();
//        if (hit) {
//            if (chain) {
//                // последний выстрел попал. и в цепочке попаданий по одному кораблю
//                calcNextShot();
//            } else {
//                // выстрел попал. но по новому кораблю
//            }
//        } else {
//            if (chain) {
//                // промах, но корабль ещё не добит
//                calcNextShot();
//            } // !hit & !chain сюда не попадёт - отсеется методом ранее
//        }
        switch (countLandedHits()) {
            case 1 -> secondShot();
            case 2 -> thirdShot();
            case 3 -> fourthShot();
        }
    }

    private int countLandedHits() {
        int landed = 0;
        for (Point point : chains.keySet()) {
            if (chains.get(point)) {
                landed++;
            }
        }
        return landed;
    }

    private void secondShot() {
        Iterator<Point> i = chains.keySet().iterator();
        Point last = new Point(0, 0);
        while (i.hasNext()) {
            last = i.next();
        }

        Point result = calculateShot(last);

        // TODO: тут ошибка: если у последнего выстрела нету доступных
        //  ячеек для выстрела, будет произведён рандомный выстрел, а надо
        //  чтобы пробовались предыдущие попадания
        if (result == null) {
            randomShot();
        } else {
            curX = result.x;
            curY = result.y;
        }
    }

    private void thirdShot() {

    }

    private void fourthShot() {

    }

    private Point calculateShot(Point last) {
        ArrayList<Point> availablePoints = new ArrayList<>();
        if (pointFree(last.plus(UP))) {
            availablePoints.add(last.plus(UP));
        }
        if (pointFree(last.plus(RIGHT))) {
            availablePoints.add(last.plus(RIGHT));
        }
        if (pointFree(last.plus(DOWN))) {
            availablePoints.add(last.plus(DOWN));
        }
        if (pointFree(last.plus(LEFT))) {
            availablePoints.add(last.plus(LEFT));
        }

        if (availablePoints.size() == 0) {
            return null;
        } else {
            if (availablePoints.size() == 1) {
                return availablePoints.get(0);
            } else {
                return availablePoints.get(random.nextInt(availablePoints.size()));
            }
        }
    }

    private boolean checkForAliveShip(Point p) {
        return (pointFree(p.plus(UP)) ||
                pointFree(p.plus(RIGHT)) ||
                pointFree(p.plus(DOWN)) ||
                pointFree(p.plus(LEFT)));
    }

    private boolean pointFree(Point p) {
        return availablePoints.contains(p);
    }

    private void randomShot() {
        Point cur = availablePoints.get(random.nextInt(availablePoints.size()));
        curX = cur.x;
        curY = cur.y;
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
