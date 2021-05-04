package app.gameController;

import app.shared.Hit;
import app.shared.HitResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

public class ShootModule {

    private static final Point UP = new Point(0, -1);
    private static final Point RIGHT = new Point(1, 0);
    private static final Point DOWN = new Point(0, 1);
    private static final Point LEFT = new Point(-1, 0);

    private final GameController owner;

    private final Random random = new Random();
    private final ArrayList<Point> availablePoints = new ArrayList<>(100);
    private final HashMap<Point, Boolean> chains = new HashMap<>();

    private boolean hit = false;
    private boolean chain = false;

    private boolean autoShot = false;

    private boolean gameEnded = false;

    private int playerNumber;
    private int turn;

    private int curX;
    private int curY;

    public ShootModule(GameController gameController) {
        owner = gameController;
    }

    public void handleManualShot(int x, int y) {
        if (myTurn()) {
            curX = x;
            curY = y;
            owner.sendShot(x ,y);
        }
    }

    public void generateOneShot() {
        if (myTurn()) {
            handleAutoShot();
            autoShot = false;
        }
    }

    public void handleAutoShot() {
        System.err.println("hit: " + hit + ", chain: " + chain);

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (myTurn()) {
            if (hit || chain) {
                nextShot();
            } else {
                randomShot();
            }
            owner.sendShot(curX, curY);
            autoShot = true;
        }
    }

    private boolean myTurn() {
        return playerNumber == turn;
    }

    private void nextShot() {
        System.err.println("player bot nextShot");
        switch (countLandedHits()) {
            case 0 -> randomShot();
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
        System.err.println("landed hits: " + landed);
        return landed;
    }

    private void secondShot() {
        System.err.println("bot second shot");
        Iterator<Point> i = chains.keySet().iterator();
        Point last = new Point(0, 0);
        while (i.hasNext()) {
            // Последним элементом будет удачное попадание, берём его.
            last = i.next();
        }
        // следующий выстрел будет произведён в случайном направлении от точки попадания
        Point result = calculateShot(last);
        if (result == null) {
            randomShot();
        } else {
            curX = result.x;
            curY = result.y;
        }
    }

    private void thirdShot() {
        System.err.println("bot third shot");
        Iterator<Point> i = chains.keySet().iterator();
        ArrayList<Point> shots = new ArrayList<>();
        // Собираем все точки, по которым было попадание
        while (i.hasNext()) {
            Point p = i.next();
            if (chains.get(p)) {
                shots.add(p);
            }
        }
        /*
         * Так как это третий выстрел, у нас гарантированно есть два попадания,
         * и корабль ещё не потоплен, а значит мы должны стрелять по вектору
         * вычисляемому из двух предыдущих выстрелов
         */
        Point vector = calculateVector(shots.get(0), shots.get(1));
        Point next = shots.get(1).plus(vector);
        // Если точка по направлению доступна для выстрела - стреляем
        if (pointFree(next)) {
            System.err.println("third shot first if next: x " + next.x + ", y " + next.y);
            curX = next.x;
            curY = next.y;
        } else {
            // Если нет - идём в обратном направлении
            next = shots.get(0).plus(vector.inverse());
            if (pointFree(next)) {
                // Если эта точка свободна - стреляем
                curX = next.x;
                curY = next.y;
            } else {
                /*
                 * Более подробное описание в боте сервера,
                 * алгоритмы идентичны
                 */
                next = calculateShot(shots.get(0));
                System.err.println("third shot third take calculated");
                if (next == null) {
                    System.err.println("next == null");
                    System.err.println("calculating new shot");
                    next = calculateShot(shots.get(1));
                    System.err.println("calculated");
                    if (next == null) {
                        System.err.println("next == null");
                        System.err.println("random shot");
                        randomShot();
                    } else {
                        System.err.println("last take next x " + next.x + ", y " + next.y);
                        curX = next.x;
                        curY = next.y;
                    }
                } else {
                    System.err.println("third take was successful");
                    System.err.println("x " + next.x + ", y " + next.y);
                    curX = next.x;
                    curY = next.y;
                }
            }
        }

    }

    private void fourthShot() {
        System.err.println("bot fourth shot");
        /*
         * Предположительно, было три попадания подрят, а значит надо добить четырёх-палубный корабль
         */
        Iterator<Point> i = chains.keySet().iterator();
        ArrayList<Point> shots = new ArrayList<>();
        // Собираем все точки, по которым было попадание
        while (i.hasNext()) {
            Point p = i.next();
            if (chains.get(p)) {
                shots.add(p);
            }
        }
        // Далее алгоритм такойже как и для третьего выстрела, тут только добавляется ещё одна ячейка для проверки
        Point vector = calculateVector(shots.get(1), shots.get(2));
        Point next = shots.get(2).plus(vector);
        if (pointFree(next)) {
            curX = next.x;
            curY = next.y;
        } else {
            // Точка по вектору не доступна для выстрела, пробуем обратное направление
            next = shots.get(0).plus(vector.inverse());
            if (pointFree(next)) {
                curX = next.x;
                curY = next.y;
            } else {
                // Ситуация такаяже, как и для третьего выстрела
                next = calculateShot(shots.get(0));
                if (next == null) {
                    next = calculateShot(shots.get(1));
                    if (next == null) {
                        next = calculateShot(shots.get(2));
                        if (next == null) {
                            randomShot();
                        } else {
                            curX = next.x;
                            curY = next.y;
                        }
                    } else {
                        curX = next.x;
                        curY = next.y;
                    }
                } else {
                    curX = next.x;
                    curY = next.y;
                }
            }
        }
    }

    private Point calculateVector(Point p1, Point p2) {
        return new Point(p2.x - p1.x, p2.y - p1.y);
    }

    private Point calculateShot(Point last) {
        ArrayList<Point> availablePoints = new ArrayList<>();
        // Собираем точки в которые можно стрелять
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
                // Если элементов больше одного - возвращаем случайный
                return availablePoints.get(random.nextInt(availablePoints.size()));
            }
        }
    }

    private boolean pointFree(Point p) {
        /*
         * Каждое обновление хода, свободные ячейки собираются в одну коллекцию.
         * Так как сущности поля здесь нет - проверка вот так сделана.
         */
        return availablePoints.contains(p);
    }

    private void randomShot() {
        // Случайная ячейка из возможных для выстрела
        Point cur = availablePoints.get(random.nextInt(availablePoints.size()));
        curX = cur.x;
        curY = cur.y;
    }

    public void handleHitResponse(HitResponse in) {
        if (in.getResponseType() == HitResponse.HIT) {
            hit = true;
            chain = true;
            chains.put(new Point(curX, curY), true);
        }

        if (in.getResponseType() == HitResponse.MISS) {
            hit = false;
        }

        if (in.getResponseType() == HitResponse.SUNK_SHIP) {
            chain = false;
            chains.clear();
        }
        System.err.println("hitResponse: hit: " + hit + ", chain: " + chain + ", chains size: " + chains.size());

        System.err.println("autoShot: " + autoShot);
//        if (autoShot && !gameEnded) {
//            handleAutoShot();
//        }
    }

    public void clearPointsList() {
        availablePoints.clear();
    }

    public void addPointToAList(int x, int y) {
        availablePoints.add(new Point(x, y));
    }

    public void setGameEnded () {
        gameEnded = true;
    }

    public void prepareToRematch() {
        gameEnded = false;
        clearPointsList();
        chains.clear();
        autoShot = false;
    }

    public void setTurn(int playerTurn) {
        turn = playerTurn;
    }

    public void setPlayerNumber(int thisPlayerNumber) {
        playerNumber = thisPlayerNumber;
    }

    public boolean isAuto() {
        return autoShot;
    }
}
