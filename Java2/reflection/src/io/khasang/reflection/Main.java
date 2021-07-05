package io.khasang.reflection;

public class Main {
    public static void main(String[] args) {
        Test test = new Test();

//        MetaClass c;
        Class c;
        c = test.getClass();
        c = Test.class;

        System.out.println(c.getName());
        System.out.println(c.getSimpleName());

        System.out.println(TestInterface.class);

        TestInterface testInterface2 = new TestInterface() {
        };

        TestInterface testInterface = new TestInterface() {
        };

        System.out.println(testInterface.getClass().getName());

    }
}
