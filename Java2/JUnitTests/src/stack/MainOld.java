package stack;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainOld {

//    public static void main(String[] args) {
//        testStack();
//        test();
//    }


    public static void testStack() {
        var stack = new GenericStack<String>(5);
        try {
            System.err.println("Stack isEmpty() " + stack.isEmpty());
            stack.push("zxcv");
            System.err.println("added 1 element to stack");
            System.err.println("Stack isEmpty() " + stack.isEmpty());
//            stack.push("zxcasd");
//            stack.push("qwedsa");
//            stack.push("cdswq");
        } catch (StackException e) {
            e.printStackTrace();
        }

        try {
            var temp1 = stack.pop();
            System.err.println(temp1);
            var temp2 = stack.pop();
            System.err.println(temp2);

            System.err.println("temp1 class: " + temp1.getClass());
            System.err.println("temp2 class: " + temp2.getClass());
        } catch (StackException ee) {
            ee.printStackTrace();
        }

        try {
            // попытаемся засунуть больше лимита
            stack.push("qwer");
        } catch (StackException e) {
            e.printStackTrace();
        }

        System.err.println("Stack size " + stack.getSize());
        var temp = stack.peek();
        System.err.println("tmep class :" + temp.getClass());
        System.err.println("stack.peek()");
        System.err.println("Stack size " + stack.getSize());

        try {
            System.err.println("Stack size " + stack.getSize());
            stack.pop();
            System.err.println("Stack size " + stack.getSize());
            stack.pop();
            System.err.println("Stack size " + stack.getSize());
            stack.pop();
            System.err.println("Stack size " + stack.getSize());
            stack.pop();
            System.err.println("Stack size " + stack.getSize());
            stack.pop();
            System.err.println("Stack size " + stack.getSize());
            stack.pop();
        } catch (StackException e2) {
            e2.printStackTrace();
        }
    }

    public static void test() {
        List<String> s = Arrays.asList("aaa", "b", "cd");
        for (String s1 : s) {
            System.err.println(s1);
        }
        // Should return {b, cd , aaa}
        Collections.sort(s, new LengthComparator());

        for (String s1 : s) {
            System.err.println(s1);
        }
    }
}
