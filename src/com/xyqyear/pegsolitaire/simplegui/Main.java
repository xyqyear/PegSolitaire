package com.xyqyear.pegsolitaire.simplegui;

import com.xyqyear.pegsolitaire.core.Core;

public class Main {
    private static Core core = Core.getInstance();
    public static void main(String[] args) {
        Gui gui = new Gui();
        gui.start();
    }
}
