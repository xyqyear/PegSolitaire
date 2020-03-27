package com.xyqyear.pegsolitaire;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonListener implements ActionListener {
    private Core core;
    private ButtonHandler buttonHandler;
    private int posX;
    private int posY;
    public ButtonListener(Core core, ButtonHandler buttonHandler, int x, int y) {
        this.core = core;
        this.buttonHandler = buttonHandler;
        this.posX = x;
        this.posY = y;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
    	System.out.println(posX + " " + posY);
    }
}
