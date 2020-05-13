package com.xyqyear.pegsolitaire.gui;

import com.xyqyear.pegsolitaire.core.Core;
import com.xyqyear.pegsolitaire.core.Position;
import com.xyqyear.pegsolitaire.core.State;
import javafx.geometry.Pos;

import javax.rmi.CORBA.Util;
import java.awt.*;

public class Game {
    private static Game singletonGame;
    private Game(){}
    public static Game getInstance() {
        if (singletonGame == null)
            singletonGame = new Game();
        return singletonGame;
    }

    private Mouse mouse = Mouse.getInstance();
    private Core core = Core.getInstance();

    private int state = 1; // 0 for pre-start, 1 for in-game

    private boolean isDragging = false;
    private Position fromPos = null;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Position getFromPos() {
        return fromPos;
    }

    public void loop() {
        if (state == 1) {
            // Piece handling
            if (!core.isFinished()) {
                // get drag start position
                if (!isDragging && mouse.isMouseLeftDown()) {
                    isDragging = true;
                    Position pos = Utils.screenPos2PiecePos(mouse.getMousePos());
                    if (pos != null && core.getBoard().getPiece(pos) == State.EXIST) {
                        fromPos = pos;
                    }
                } else if (isDragging && !mouse.isMouseLeftDown()) {
                    isDragging = false;
                    Position toPos = Utils.screenPos2PiecePos(mouse.getMousePos());
                    core.doStep(fromPos, toPos);
                    fromPos = null;
                }
            }
        }
    }
}
