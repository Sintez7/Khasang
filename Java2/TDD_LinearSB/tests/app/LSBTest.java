package app;

import org.junit.*;

import static org.junit.Assert.*;

public class LSBTest {

    LSB app;

    @Before
    public void setUp() throws Exception {
        app = new LSB();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Ignore
    @Test
    public void fullTestLSB() {
        app = new LSB()
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
//        char[] temp = new char[LSB.SIZE];
        char[] testing = app.getField();
//        for (int i = 0; i < testing.length; i++) {
//            switch (testing[i]) {
//                case '.' ->
//            }
//
//        }
    }
}