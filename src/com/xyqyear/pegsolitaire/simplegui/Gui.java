package com.xyqyear.pegsolitaire;

import javax.swing.*;
import java.awt.*;

public class Gui extends JFrame {
    public Gui() {
        ButtonHandler buttonHandler = new ButtonHandler();
        Container c = getContentPane();
        buttonHandler.addButtonsToGui(c);
        setLayout(new GridLayout(7,7,1,1));
        setTitle("Peg Solitaire");
        setSize(450, 440);
    }

    public void start(){
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
