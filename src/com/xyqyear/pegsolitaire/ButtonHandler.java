package com.xyqyear.pegsolitaire;

import javax.swing.*;
import java.awt.*;

public class ButtonHandler extends JFrame {
    private static JButton[][] buttons =new JButton[7][7];
    private Core core;
    private boolean firstButton = true;
    private int fromX;
    private int fromY;
    public ButtonHandler (Core core) {
        this.core = core;
        for (int y = 0; y < 7; y++) {
            for (int x = 0; x < 7; x++) {
                buttons[x][y] = new JButton();
                buttons[x][y].addActionListener(new ButtonListener(core, this, x, y));
            }
        }
    }

    public boolean isFirstButton() {
        return firstButton;
    }

    public void setFirstButton(boolean firstButton) {
        this.firstButton = firstButton;
    }

    public int getFromX() {
        return fromX;
    }

    public void setFromX(int fromX) {
        this.fromX = fromX;
    }

    public int getFromY() {
        return fromY;
    }

    public void setFromY(int fromY) {
        this.fromY = fromY;
    }

    private void setText(JButton button, State state) {
        switch (state) {
            case DISABLED:
                button.setEnabled(false);
                break;
            case EXIST:
                button.setText("●");
                break;
            case TAKEN:
                button.setText("○");
                break;
        }
    }

    public void refreshBoard() {
        Board board = core.getBoard();
        for (int y = 0; y < 7; y++) {
            for (int x = 0; x < 7; x++) {
                setText(buttons[x][y], board.getPiece(x, y));
            }
        }
    }

    public void addButtonsToGui(Container c) {
        // handle pieces
        Board board = core.getBoard();
        refreshBoard();
        for (int y = 0; y < 7; y++) {
            for (int x = 0; x < 7; x++) {
                c.add(buttons[x][y]);
            }
        }

        // handle functional buttons
        buttons[5][0].setEnabled(true);
        buttons[5][0].setText("悔棋");
        buttons[6][0].setEnabled(true);
        buttons[6][0].setText("重置");
    }
}
