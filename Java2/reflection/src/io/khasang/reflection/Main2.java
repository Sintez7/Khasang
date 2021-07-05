package io.khasang.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main2 {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {
        Class<Test> manager = Test.class;

        Test test;
//        test = new Test();
        test = manager.newInstance();
        test.foo();

        Method method = manager.getMethod("foo");
        System.out.println(method.toString());
        System.out.println(method.getName());

        method.invoke(new Test());

//        Field field = manager.getField("field");
        Field field = manager.getDeclaredField("field");
        field.setAccessible(true);
        field.set(test, 42);

        System.out.println(test);
    }
}
