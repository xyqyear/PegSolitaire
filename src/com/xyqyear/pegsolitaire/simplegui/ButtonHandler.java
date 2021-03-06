package com.xyqyear.pegsolitaire.simplegui;

import com.xyqyear.pegsolitaire.core.Board;
import com.xyqyear.pegsolitaire.core.Core;
import com.xyqyear.pegsolitaire.core.Position;
import com.xyqyear.pegsolitaire.core.State;

import javax.swing.*;
import java.awt.*;

public class ButtonHandler extends JFrame {
    private JButton[][] buttons = new JButton[7][7];
    private Core core = Core.getInstance();
    private boolean firstButton = true;
    private int fromX;
    private int fromY;
    public ButtonHandler () {
        for (int y = 0; y < 7; y++) {
            for (int x = 0; x < 7; x++) {
                buttons[x][y] = new JButton();
                buttons[x][y].addActionListener(new ButtonListener(this, x, y));
            }
        }
    }

    public JButton getButtonAt(int x, int y) {
        return this.buttons[x][y];
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

    private void setButtonState(int x, int y, State state) {
        JButton button = getButtonAt(x, y);
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
                if (core.isInBoard(new Position(x, y)))
                    setButtonState(x, y, board.getPiece(x, y));
            }
        }
    }

    public void initBoard() {
        Board board = core.getBoard();
        for (int y = 0; y < 7; y++) {
            for (int x = 0; x < 7; x++) {
                setButtonState(x, y, board.getPiece(x, y));
            }
        }
    }

    public void addButtonsToGui(Container c) {
        initBoard();
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
