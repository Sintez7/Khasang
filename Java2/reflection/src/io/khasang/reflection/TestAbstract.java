package io.khasang.reflection;

import java.io.Serializable;
import java.util.Arrays;

public abstract class TestAbstract extends Test implements Serializable, Cloneable {
    private static int privateStaticField;
    private int field;
    private String name;
    public String[] cells;

    public TestAbstract() {
    }

    private TestAbstract(int field) {
        this.field = field;
    }

    public TestAbstract(int field, String name) {
        this.field = field;
        this.name = name;
    }

    public static int getPrivateStaticField() {
        return privateStaticField;
    }

    public static void setPrivateStaticField(int privateStaticField) {
        TestAbstract.privateStaticField = privateStaticField;
    }

    public int getField() {
        return field;
    }

    public void setField(int field) {
        this.field = field;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getCells() {
        return cells;
    }

    public void setCells(String[] cells) {
        this.cells = cells;
    }

    @Override
    public String toString() {
        return "TestAbstract{" +
                "field=" + field +
                ", name='" + name + '\'' +
                ", cells=" + Arrays.toString(cells) +
                '}';
    }
}
