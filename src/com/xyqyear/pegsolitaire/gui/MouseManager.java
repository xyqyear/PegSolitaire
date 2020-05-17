package com.xyqyear.pegsolitaire.gui;

import com.xyqyear.pegsolitaire.core.Position;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseManager implements MouseMotionListener, MouseListener {
    // singleton stuff
    private static MouseManager singletonMouseManager;
    private MouseManager(){}
    public static MouseManager getInstance() {
        if (singletonMouseManager == null)
            singletonMouseManager = new MouseManager();
        return singletonMouseManager;
    }

    private Position mousePos = new Position();
    private Position mouseOnScreenPos = new Position();
    private boolean mouseLeftDown = false;

    public boolean isMouseLeftDown() {
        return mouseLeftDown;
    }

    public Position getMousePos() {
        return mousePos;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mousePos.setPosition(e.getPoint());
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            mousePos.setPosition(e.getPoint());
            mouseOnScreenPos.setPosition(e.getLocationOnScreen());
            mouseLeftDown = true;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            mousePos.setPosition(e.getPoint());
            mouseLeftDown = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            mouseLeftDown = false;
        }
    }
}
