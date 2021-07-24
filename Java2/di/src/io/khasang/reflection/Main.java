package io.khasang.reflection;

import io.khasang.reflection.di.Context;

public class Main {
    public static void main(String[] args) {
        Context context = new Context("config.xml");
        var car = context.<Car>getBean(Car.class); // DZ generics

        System.out.println(car);
    }
}
