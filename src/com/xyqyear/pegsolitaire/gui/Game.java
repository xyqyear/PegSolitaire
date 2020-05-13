package com.xyqyear.pegsolitaire.gui;

import com.xyqyear.pegsolitaire.core.Core;
import com.xyqyear.pegsolitaire.core.Position;
import com.xyqyear.pegsolitaire.core.State;

public class Game {
    private static Game singletonGame;
    private Game(){}
    public static Game getInstance() {
        if (singletonGame == null)
            singletonGame = new Game();
        return singletonGame;
    }

    private Mouse mouse = Mouse.getInstance();
    private Keyboard keyboard = Keyboard.getInstance();
    private Core core = Core.getInstance();

    private int state = 1; // 0 for pre-start, 1 for in-game

    private boolean isDragging = false;
    private Position fromPos = null;
    private Position pieceRenderOffset = new Position(0 ,0);

    public int getState() {
        return state;
    }

    public Position getFromPos() {
        return fromPos;
    }

    public Position getPieceRenderOffset() {
        return pieceRenderOffset;
    }

    public void setPieceRenderOffset(int x, int y) {
        this.pieceRenderOffset.setPosition(x, y);
    }

    public void loop() {
        if (state == 1) {
            // handle escape key
            if (keyboard.shouldGamePause()) {
                state = 0;
                return;
            }

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
                    Position mousePos = new Position(mouse.getMousePos());
                    // visual offset
                    mousePos.move(pieceRenderOffset.getX() + 26, pieceRenderOffset.getY() + 26);
                    Position toPos = Utils.screenPos2PiecePos(mousePos);
                    core.doStep(fromPos, toPos);
                    fromPos = null;
                }
            }
        } else {
            if (!keyboard.shouldGamePause()) {
                state = 1;
            }
        }
    }
}
