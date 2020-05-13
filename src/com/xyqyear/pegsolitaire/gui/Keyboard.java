package com.xyqyear.pegsolitaire.gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {
    // singleton stuff
    private static Keyboard singletonKeyboard;
    private Keyboard(){}
    public static Keyboard getInstance() {
        if (singletonKeyboard == null)
            singletonKeyboard = new Keyboard();
        return singletonKeyboard;
    }

    private boolean shouldGamePause = false;

    public boolean shouldGamePause() {
        return shouldGamePause;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 27) {
            shouldGamePause = !shouldGamePause;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
