package com.xyqyear.pegsolitaire;

public class Main {
    private static Core core = new Core();
    public static void main(String[] args) {
        Gui gui = new Gui(core);
        gui.start();
    }
}
