package app;

import org.junit.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import static org.junit.Assert.*;

public class LSBTest {

    LSB app;
    Random random = new Random();

    @Before
    public void setUp() throws Exception {
        app = new LSB();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void fullTestLSB() {
        String moves = "1\n2\n3\n4\n5\n6\n7\n8\n9\n10";
        Scanner scanner = new Scanner(moves);
        app = new LSB(new Input() {
            @Override
            public int getShot() {
                return scanner.nextInt();
            }
        });
        app.init();
        app.play();
    }

    @Test
    public void fieldTestUS1() {
        char[] test = new char[LSB.SIZE];
        char[] testing = app.getField();

        assertArrayEquals(test, testing);
    }

    @Test
    public void fieldInitTestUS2() {
        app.initField();
        char[] testing = app.getField();
        for (char c : testing) {
            assertEquals('.', c);
        }
    }

    @Test
    public void testHiddenShipUS3() {
        app.init();
        int pos = app.getShipPos();
        assertTrue(pos > 0 && pos < LSB.SIZE);
    }

    @Test
    public void testShotUS4() {
        app.initField();
        int result = app.shoot(5);
        assertEquals(2, result);
    }

    @Test
    public void testShowingUS5() {
        app.init();
        int temp;
        char[] testing;
        do {
            temp = random.nextInt(LSB.SIZE);
        } while (temp == app.getShipPos() + 1 && temp > 0);

        app.shoot(temp);
        testing = app.getField();
        assertEquals('+', testing[temp - 1]);

        app.shoot(app.getShipPos() + 1);
        testing = app.getField();
        assertEquals('x', testing[app.getShipPos()]);
    }

    @Test
    public void testMessageUS6() {
        List<String> messages = new ArrayList<>();

        Output mockOutput = new Output() {
            @Override
            public void send(String text) {
                System.err.println(text);
                messages.add(text);
            }
        };

        app = new LSB(mockOutput);
        app.init();

        int temp;
        do {
            temp = random.nextInt(LSB.SIZE);
        } while (temp == app.getShipPos() && temp > 0);

        app.shoot(temp);
        assertEquals(LSB.MISS_MESSAGE, messages.get(messages.size() - 1));

        app.shoot(app.getShipPos() + 1);
        assertEquals(LSB.HIT_MESSAGE, messages.get(messages.size() - 3));
        assertEquals(LSB.WIN_MESSAGE, messages.get(messages.size() - 2));
        assertEquals(LSB.MOVES_COUNT_MESSAGE + app.getShotsCount(), messages.get(messages.size() - 1));
    }

    @Test
    public void testUserFriendlyInputUS7() {
        do {
            app.init();
        } while (app.getShipPos() != 1 && app.getShipPos() != 10);

        assertEquals(0, app.shoot(0));
        assertEquals(0, app.shoot(11));
        assertEquals(2, app.shoot(1));
        assertEquals(2, app.shoot(10));
    }

    @Test
    public void testWinConditionUS8() {
        app.init();
        assertEquals(1, app.shoot(app.getShipPos() + 1));
        assertEquals(1, app.getShotsCount());
    }
}