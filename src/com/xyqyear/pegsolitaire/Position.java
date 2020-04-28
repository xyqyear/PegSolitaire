package com.xyqyear.pegsolitaire;

public class Position {
    private int x;
    private int y;

    public Position (int initX, int initY) {
        this.x = initX;
        this.y = initY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
