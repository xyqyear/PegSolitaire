package com.xyqyear.pegsolitaire.gui;

import javax.swing.*;

public class SingletonJFrame {
    private static JFrame singletonJFrame;
    public static JFrame getInstance() {
        if (singletonJFrame == null)
            singletonJFrame = new JFrame();
        return singletonJFrame;
    }
}
