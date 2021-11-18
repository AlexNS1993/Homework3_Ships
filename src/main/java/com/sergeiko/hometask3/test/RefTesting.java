package com.sergeiko.hometask3.test;

public class RefTesting {
    private int x;

    public RefTesting(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override
    public String toString() {
        return "RefTesting{" +
                "x=" + x +
                '}';
    }
}
