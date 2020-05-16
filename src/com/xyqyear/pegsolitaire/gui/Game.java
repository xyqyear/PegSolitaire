package com.xyqyear.pegsolitaire.gui;

import com.xyqyear.pegsolitaire.core.Core;
import com.xyqyear.pegsolitaire.core.Position;
import com.xyqyear.pegsolitaire.core.State;

public class Game {
    private static Game singletonGame;
    private Game() {}
    public static Game getInstance() {
        if (singletonGame == null)
            singletonGame = new Game();
        return singletonGame;
    }

    private Mouse mouse = Mouse.getInstance();
    private Keyboard keyboard = Keyboard.getInstance();
    private Core core = Core.getInstance();

    private int state = 0; // 0 for paused, 1 for in-game
    private boolean shouldRerender = true;

    private Position fromPos = null;
    private Position pieceRenderOffset = new Position();

    // Position for buttons
    private final Position buttonSize = new Position(225, 55);
    private MenuButton[] menuButtons = new MenuButton[]{
            new MenuButton(0, "回到游戏", new Position(190, 110)),
            new MenuButton(1, "  悔棋  ", new Position(190, 190)),
            new MenuButton(2, "重新开始", new Position(190, 270)),
            new MenuButton(3, "", new Position(190, 350)),
            new MenuButton(4, "", new Position(190, 430))
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

    public void setPieceRenderOffset(int x, int y) {
        this.pieceRenderOffset.setPosition(x, y);
    }

    public boolean shouldRerender() {
        return shouldRerender;
    }

    public void setShouldRerender(boolean needUpdate) {
        this.shouldRerender = needUpdate;
    }

    public void loop() {
        // handle button availability
        // if the takeBack button should not be available but it does
        if ((core.getBoard().getPieceNum() == 32 || core.isFinished())) {
            if (menuButtons[1].isAvailable()) {
                menuButtons[1].setAvailable(false);
                setShouldRerender(true);
            }
        // else if the the button should be available but it doesn't
        } else if (!menuButtons[1].isAvailable()) {
            menuButtons[1].setAvailable(true);
            setShouldRerender(true);
        }

        if (state == 1) {
            // handle escape key
            if (keyboard.shouldGamePause()) {
                state = 0;
                setShouldRerender(true);
                return;
            }

            // Piece handling
            if (!core.isFinished()) {
                // get drag start position
                if (fromPos == null && mouse.isMouseLeftDown()) {
                    Position pos = Utils.screenPos2PiecePos(mouse.getMousePos());
                    if (pos != null && core.getBoard().getPiece(pos) == State.EXIST) {
                        fromPos = pos;
                        setShouldRerender(true);
                    }
                } else if (fromPos != null && !mouse.isMouseLeftDown()) {
                    Position mousePos = new Position(mouse.getMousePos());
                    // visual offset
                    mousePos.move(pieceRenderOffset.getX() + 26, pieceRenderOffset.getY() + 26);
                    Position toPos = Utils.screenPos2PiecePos(mousePos);
                    core.doStep(fromPos, toPos);
                    fromPos = null;
                    setShouldRerender(true);
                }
            }
        } else {
            if (!keyboard.shouldGamePause()) {
                state = 1;
                setShouldRerender(true);
                return;
            }
            for (MenuButton menuButton : menuButtons) {
                if (Utils.inRange(mouse.getMousePos(), menuButton.getPosition(), buttonSize)) {
                    if (!menuButton.isHovering()) {
                        menuButton.setHovering(true);
                        setShouldRerender(true);
                    }
                    if (mouse.isMouseLeftDown() && !menuButton.isPushing()) {
                        menuButton.setPushing(true);
                        setShouldRerender(true);
                    }
                    else if (!mouse.isMouseLeftDown() && menuButton.isPushing()) {
                        // button push logic
                        menuButton.setPushing(false);
                        if (menuButton.isAvailable()) {
                            switch (menuButton.getId()) {
                                case 0:
                                    keyboard.setShouldGamePause(false);
                                    break;
                                case 1:
                                    core.takeBack();
                                    break;
                                case 2:
                                    core.init();
                            }
                        }
                        setShouldRerender(true);
                    }
                } else {
                    if (menuButton.isHovering() || menuButton.isPushing()) {
                        setShouldRerender(true);
                        menuButton.setHovering(false);
                        menuButton.setPushing(false);
                    }
                }
            }
        }
    }
}
