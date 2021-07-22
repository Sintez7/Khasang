package io.khasang.reflection;

public abstract class Gear {
    protected String type;
    protected int count;

    public Gear() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    abstract void nextGear();
    abstract void prevGear();

    @Override
    public String toString() {
        return "Gear{" +
                "type='" + type + '\'' +
                ", count=" + count +
                '}';
    }
}
