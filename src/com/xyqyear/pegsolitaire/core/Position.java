package com.xyqyear.pegsolitaire.core;

import java.awt.*;

public class Position {
    private int x;
    private int y;

    public Position (int initX, int initY) {
        this.x = initX;
        this.y = initY;
    }

    public Position (Point point) {
        this.x = (int) point.getX();
        this.y = (int) point.getY();
    }

    public Position (Position position) {
        this.x = position.getX();
        this.y = position.getY();
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

    public void setPosition(Position position) {
        this.x = position.getX();
        this.y = position.getY();
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setPosition(Point point) {
        this.x = (int) point.getX();
        this.y = (int) point.getY();
    }

    public boolean equal(int x, int y) {
        return this.x == x && this.y == y;
    }

    public boolean equal(Position position) {
        return equal(position.getX(), position.getY());
    }

    public void move(int x, int y) {
        this.x += x;
        this.y += y;
    }

    public void move(Position position) {
        move(position.getX(), position.getY());
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
