package io.khasang.reflection;

public class Engine { // POJO
    private int power;

    public Engine() {
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    @Override
    public String toString() {
        return "Engine{" +
                "power=" + power +
                '}';
    }
}
