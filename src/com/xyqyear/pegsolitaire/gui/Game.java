package com.xyqyear.pegsolitaire.gui;

import com.xyqyear.pegsolitaire.core.Core;
import com.xyqyear.pegsolitaire.core.Position;
import com.xyqyear.pegsolitaire.core.State;

import javax.rmi.CORBA.Util;

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

    private int state = 1; // 0 for paused, 1 for in-game

    private boolean isDragging = false;
    private Position fromPos = null;
    private Position pieceRenderOffset = new Position(0 ,0);

    // Position for buttons
    private final Position buttonSize = new Position(230, 60);
    private MenuButton[] menuButtons = new MenuButton[]{
            new MenuButton("resume", new Position(190, 110)),
            new MenuButton("takeBack", new Position(190, 190)),
            new MenuButton("restart", new Position(190, 270)),
            new MenuButton("record", new Position(190, 350)),
            new MenuButton("ranking", new Position(190, 430))
    };

    public int getState() {
        return state;
    }

    public Position getFromPos() {
        return fromPos;
    }

    public Position getPieceRenderOffset() {
        return pieceRenderOffset;
    }

    public MenuButton[] getMenuButtons() {
        return menuButtons;
    }

    public Position getButtonSize() {
        return buttonSize;
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

            for (MenuButton menuButton : menuButtons) {
                if (Utils.inRange(mouse.getMousePos(), menuButton.getPosition(), buttonSize)) {
                    menuButton.setHovering(true);
                    if (mouse.isMouseLeftDown() && !menuButton.isPushing())
                        menuButton.setPushing(true);
                    else if (!mouse.isMouseLeftDown() && menuButton.isPushing()) {
                        // button push logic
                        menuButton.setPushing(false);


                    }
                } else {
                    menuButton.setHovering(false);
                    menuButton.setPushing(false);
                }
            }
        }
    }
}
