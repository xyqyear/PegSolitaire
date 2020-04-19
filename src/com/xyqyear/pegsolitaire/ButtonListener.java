package com.xyqyear.pegsolitaire;

import java.awt.*;
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
        // 如果是第一次按下，那么就把判断第一次按下的设置为false
        // 并且设置第一次按下的坐标为x和y
    	if (this.buttonHandler.isFirstButton()) {
    		this.buttonHandler.setFirstButton(false);
    		this.buttonHandler.setFromX(this.posX);
    		this.buttonHandler.setFromY(this.posY);
    		this.buttonHandler.getButtonAt(posX, posY).setForeground(Color.red);
    	}
    	// 如果不是第一次按下，那么就先设置第一次按下的值为true
        // 然后走棋。
    	else {
    		this.buttonHandler.setFirstButton(true);
            this.buttonHandler.getButtonAt(this.buttonHandler.getFromX(), this.buttonHandler.getFromY()).setForeground(Color.black);
    	}
    }
}
