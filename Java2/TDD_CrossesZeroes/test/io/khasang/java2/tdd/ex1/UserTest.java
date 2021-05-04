package io.khasang.java2.tdd.ex1;

import game.ConsoleShootStrategy;
import game.MockShootStratagy;
import game.Point;
import game.User;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;

public class UserTest {
    private User user;

    @Test
    public void testGetShootPoint() {
        user = new User(new MockShootStratagy(), 'X');
        assertEquals(new Point(0, 0), user.getShootPoint());
    }

    @Test
    public void testConsoleInputStrategy() throws UnsupportedEncodingException {
        String mockInput = "0 0\n0 1\n0 2\n";
        InputStream mockInputStream = new ByteArrayInputStream(mockInput.getBytes(StandardCharsets.UTF_8.name()));

        ConsoleShootStrategy consoleShootStrategy = new ConsoleShootStrategy(mockInputStream);
        assertEquals(new Point(0, 0), consoleShootStrategy.getShootPoint());
        assertEquals(new Point(0, 1), consoleShootStrategy.getShootPoint());
        assertEquals(new Point(0, 2), consoleShootStrategy.getShootPoint());
    }
}