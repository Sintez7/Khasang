package io.khasang.reflection;

public class Test {
    private int field = 10;

    public void foo() {
        System.out.println("This is foo!!!");
    }

    @Override
    public String toString() {
        return "Test{" +
                "field=" + field +
                '}';
    }
}
