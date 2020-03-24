package com.xyqyear.pegsolitaire;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonListener implements ActionListener {
    private Core core;
    private ButtonHandler buttonHandler;
    public ButtonListener(Core core, ButtonHandler buttonHandler) {
        this.core = core;
        this.buttonHandler = buttonHandler;
    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
