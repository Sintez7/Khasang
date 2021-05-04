package stack;

import org.junit.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class StackTest {

    /*
     * Напишите юнит-тесты для задания из предыдущего занятия: Обобщенный стек на Java.
     * 1) Настройте и подключите библиотеку JUnit4
     * 2) Напишите юнит-тесты для каждого метода
     * 3) Убедитесь в том, что вы покрыли все наиболее частые ошибки,
     * которые могут возникнуть при рефакторинге вашего обобщенного стека
     */

    // add new element to the top of the stack
    @Test
    public void testPush() {
        Stack<Object> stack = new GenericStack<>(10);
        try {
            stack.push(new Object());
        } catch (StackException e) {
            e.printStackTrace();
        }
        assertEquals(1, stack.getSize());
    }

    @Test(expected = StackException.class)
    public void testPushException() throws StackException {
        Stack<Object> stack = new GenericStack<>(2);
        stack.push(new Object());
        stack.push(new Object());
        stack.push(new Object());
    }

    // return and remove an element from the top
    @Test
    public void testPop() throws StackException {
        Stack<String> stack = new GenericStack<>(3);
        String tO1 = "testing string";
        stack.push("new Object()");
        stack.push(tO1);
        assertEquals(tO1, stack.pop());
    }

    @Test(expected = StackException.class)
    public void testPopException() throws StackException {
        Stack<Object> stack = new GenericStack<>(2);
        stack.pop();
    }

    // return the top element but doesn’t remove
    @Test
    public void testPeek() throws StackException {
        Stack<String> stack = new GenericStack<>(3);
        assertNull(stack.peek());
        String tO1 = "testing string";
        stack.push("new Object()");
        stack.push(tO1);
        assertEquals(tO1, stack.peek());
    }

    @Test
    public void testGetSize() throws StackException {
        Stack<Object> stack = new GenericStack<>(2);
        assertEquals(0, stack.getSize());
        stack.push(new Object());
        assertEquals(1, stack.getSize());
        stack.push(new Object());
        assertEquals(2, stack.getSize());
        stack.pop();
        assertEquals(1, stack.getSize());
        stack.peek();
        assertEquals(1, stack.getSize());
    }

    @Test
    public void testIsEmpty() throws StackException {
        Stack<Object> stack = new GenericStack<>(2);
        assertTrue(stack.isEmpty());
        stack.push(new Object());
        assertFalse(stack.isEmpty());
    }

    @Test
    public void testIsFull() throws StackException {
        Stack<Object> stack = new GenericStack<>(2);
        assertFalse(stack.isFull());
        stack.push(new Object());
        assertFalse(stack.isFull());
        stack.push(new Object());
        assertTrue(stack.isFull());
        stack.pop();
        assertFalse(stack.isFull());
    }

    // add all elements from @src to the stack
    @Test
    public void testPushAll() throws StackException {
        Stack<String> stack = new GenericStack<>(5);
        List<String> list = new ArrayList<>();
        String s1 = "asdf";
        String s2 = "zxcv";
        String s3 = "qwer";
        String s4 = "rewq";
        String s5 = "fdsa";

        stack.push(s1);

        list.add(s2);
        list.add(s3);
        list.add(s4);

        stack.pushAll(list);

        stack.push(s5);

        assertEquals(s5, stack.pop());
        assertEquals(s4, stack.pop());
        assertEquals(s3, stack.pop());
        assertEquals(s2, stack.pop());
        assertEquals(s1, stack.pop());
    }

    @Test(expected = StackException.class)
    public void testPushAllException() throws StackException {
        Stack<String> stack = new GenericStack<>(3);
        List<String> list = new ArrayList<>();

        list.add("qwer");
        list.add("asdf");
        list.add("zxcv");
        list.add("qaz");

        stack.pushAll(list);
    }

    // pop all elements from stack to @dst
    @Test
    public void testPopAll() throws StackException {
        Stack<String> stack = new GenericStack<>(3);
        List<String> list = new ArrayList<>();

        String s1 = "asdf";
        String s2 = "zxcv";
        String s3 = "qwer";

        stack.push(s1);
        stack.push(s2);
        stack.push(s3);

        stack.popAll(list);

        assertEquals(0, stack.getSize());
        assertEquals(3, list.size());

        assertEquals(s3, list.get(0));
        assertEquals(s2, list.get(1));
        assertEquals(s1, list.get(2));
    }

    @Ignore("Currently method popAll() can't throw exception")
    @Test(expected = StackException.class)
    public void testPopAllException() throws StackException {
        Stack<String> stack = new GenericStack<>(3);
        List<String> list = new ArrayList<>();

        String s1 = "asdf";
        String s2 = "zxcv";
        String s3 = "qwer";

        stack.push(s1);
        stack.push(s2);
        stack.push(s3);

        stack.popAll(list);
        assertEquals(0, stack.getSize());
        assertEquals(3, list.size());
    }
}