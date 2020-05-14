package com.xyqyear.pegsolitaire.gui;

import com.xyqyear.pegsolitaire.core.Position;

public class MenuButton {
    private String id;
    private Position position;
    private boolean hovering;
    private boolean pushing;

    public MenuButton (String id, Position position) {
        this.id = id;
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public String getId() {
        return id;
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
