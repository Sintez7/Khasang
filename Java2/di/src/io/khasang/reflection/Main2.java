package io.khasang.reflection;

public class Main2 {
    public static void main(String[] args) {
        Engine engine = new Engine();
        engine.setPower(200);
        Gear gear = new Manual();

        Car car = new Car();
        // DI
        car.setEngine(engine);
        car.setGear(gear);

        System.out.println(car);
    }
}
