package com.xyqyear.pegsolitaire.gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardManager implements KeyListener {
    // singleton stuff
    private static KeyboardManager singletonKeyboardManager;
    private KeyboardManager(){}
    public static KeyboardManager getInstance() {
        if (singletonKeyboardManager == null)
            singletonKeyboardManager = new KeyboardManager();
        return singletonKeyboardManager;
    }

    private boolean shouldGamePause = true;

    public boolean shouldGamePause() {
        return shouldGamePause;
    }

    public void setShouldGamePause(boolean shouldGamePause) {
        this.shouldGamePause = shouldGamePause;
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
