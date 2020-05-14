package com.xyqyear.pegsolitaire.gui;

import com.xyqyear.pegsolitaire.core.Position;

public class MenuButton {
    private int id;
    private String buttonString;
    private Position position;

    private boolean available;
    private boolean hovering;
    private boolean pushing;

    public MenuButton (int id, String buttonString, Position position) {
        this.id = id;
        this.buttonString = buttonString;
        this.position = position;

        available = true;
    }

    public Position getPosition() {
        return position;
    }

    public int getX() {
        return position.getX();
    }

    public int getY() {
        return position.getY();
    }

    public int getId() {
        return id;
    }

    public String getButtonString() {
        return buttonString;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isHovering() {
        return hovering;
    }

    public void setHovering(boolean hovering) {
        this.hovering = hovering;
    }

    public boolean isPushing() {
        return pushing;
    }

    public void setPushing(boolean pushing) {
        this.pushing = pushing;
    }
}
