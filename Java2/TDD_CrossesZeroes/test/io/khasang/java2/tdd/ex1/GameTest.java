package io.khasang.java2.tdd.ex1;

import game.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GameTest {
    private Game game;

    @Before
    public void setUp() throws Exception {
        game = new Game();
        game.initCells();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testArrayUs1() {
        assertEquals(Game.SIZE, game.getCells().length);
        assertEquals(Game.SIZE, game.getCells()[0].length);
    }

    @Test
    public void testInitUs2() {
        for (int i = 0; i < Game.SIZE; i++) {
            for (int j = 0; j < Game.SIZE; j++) {
                assertEquals('.', game.getCharAtPoint(new Point(i, j)));
            }
        }
    }

    @Test
    public void testIsXWinUs3() {
        game.setShootPoint(new Point(0, 0), 'X');
        game.setShootPoint(new Point(0, 1), 'X');
        game.setShootPoint(new Point(0, 2), 'X');
        assertEquals(Game.Status.X_WIN, game.getGameStatus());
    }

    @Test
    public void testSetShootPointUs4() {
        Point point = new Point(1, 0);
        char x = 'X';
        game.setShootPoint(point, x);
        assertEquals(x, game.getCharAtPoint(point));
    }

    @Test
    public void testGetFieldForOutputUs5() {
        String field = ". . .\n. . .\n. . .\n";
        assertEquals(field, game.getFieldForOutput());
    }

    @Test
    public void testGetFieldForOutputWithOneStep() {
        Point point = new Point(1, 0);
        char x = 'X';
        game.setShootPoint(point, x);
        String field = ". X .\n. . .\n. . .\n";
        assertEquals(field, game.getFieldForOutput());
    }

    @Test
    public void testShootOnlyOnEmptyCellUs6() {
        Point point = new Point(1, 0);
        char x = 'X';
        boolean result;
        result = game.setShootPoint(point, x);
        assertTrue(result);
        result = game.setShootPoint(point, x);
        assertFalse(result);
    }

    @Test
    public void testDraw() {
        char[][] cells = {
                {'X', 'O', 'X'},
                {'O', 'O', 'X'},
                {'X', 'X', 'O'}
        };
        for (int i = 0; i < Game.SIZE; i++) {
            for (int j = 0; j < Game.SIZE; j++) {
                game.setShootPoint(new Point(j, i), cells[j][i]);
            }
        }
        assertEquals(Game.Status.DRAW, game.getGameStatus());
    }

    @Test
    public void testGameStart() throws UnsupportedEncodingException {
//        String mockInputForUser1 = "0 0\n0 1\n0 2\n";
//        String mockInputForUser2 = "2 0\n1 1\n2 2\n";
//        InputStream mockInputStream1 = new ByteArrayInputStream(mockInputForUser1.getBytes(StandardCharsets.UTF_8.name()));
//        InputStream mockInputStream2 = new ByteArrayInputStream(mockInputForUser2.getBytes(StandardCharsets.UTF_8.name()));

//        ConsoleShootStrategy consoleShootStrategy1 = new ConsoleShootStrategy(mockInputStream1);
//        ConsoleShootStrategy consoleShootStrategy2 = new ConsoleShootStrategy(mockInputStream2);

        ShootStrategy consoleShootStrategy1 = new RandomShootStratagy();
        ShootStrategy consoleShootStrategy2 = new RandomShootStratagy();

        User user1 = new User(consoleShootStrategy1, 'X');
        User user2 = new User(consoleShootStrategy2, 'O');

        game.setUser1(user1);
        game.setUser2(user2);

        game.initCells();
        game.start();

        assertEquals(Game.Status.X_WIN, game.getGameStatus());
    }
}