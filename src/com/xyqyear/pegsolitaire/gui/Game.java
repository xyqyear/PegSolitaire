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

    private MouseManager mouseManager = MouseManager.getInstance();
    private KeyboardManager keyboardManager = KeyboardManager.getInstance();
    private Core core = Core.getInstance();
    private RecordManager recordManager = RecordManager.getInstance();

    private int state = 0; // 0 for paused, 1 for in-game
    private boolean shouldRerender = true;

    private Position fromPos = null;
    private Position pieceRenderOffset = new Position();

    // Position for buttons
    private final Position buttonSize = new Position(225, 55);
    private MenuButton[] menuButtons = new MenuButton[]{
            new MenuButton("回到游戏"),
            new MenuButton("  悔棋  "),
            new MenuButton("重新开始"),
            new MenuButton("退出游戏")
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

    public boolean loop() {
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
            if (keyboardManager.shouldGamePause()) {
                state = 0;
                setShouldRerender(true);
                return true;
            }

            // Piece handling
            if (!core.isFinished()) {
                // get drag start position
                if (fromPos == null && mouseManager.isMouseLeftDown()) {
                    Position pos = Utils.screenPos2PiecePos(mouseManager.getMousePos());
                    if (pos != null && core.getBoard().getPiece(pos) == State.EXIST) {
                        fromPos = pos;
                        setShouldRerender(true);
                    }
                } else if (fromPos != null && !mouseManager.isMouseLeftDown()) {
                    Position mousePos = new Position(mouseManager.getMousePos());
                    // visual offset
                    mousePos.move(pieceRenderOffset.getX() + 26, pieceRenderOffset.getY() + 26);
                    Position toPos = Utils.screenPos2PiecePos(mousePos);
                    core.doStep(fromPos, toPos);
                    if (core.isFinished()) {
                        recordManager.addRecord(core.getBoard().getPieceNum());
                        keyboardManager.setShouldGamePause(true);
                    }
                    fromPos = null;
                    setShouldRerender(true);
                }
            }
        } else {
            if (!keyboardManager.shouldGamePause()) {
                state = 1;
                setShouldRerender(true);
                return true;
            }
            for (MenuButton menuButton : menuButtons) {
                if (Utils.inRange(mouseManager.getMousePos(), menuButton.getPosition(), buttonSize)) {
                    if (!menuButton.isHovering()) {
                        menuButton.setHovering(true);
                        setShouldRerender(true);
                    }
                    if (mouseManager.isMouseLeftDown() && !menuButton.isPushing()) {
                        menuButton.setPushing(true);
                        setShouldRerender(true);
                    }
                    else if (!mouseManager.isMouseLeftDown() && menuButton.isPushing()) {
                        // button push logic
                        menuButton.setPushing(false);
                        if (menuButton.isAvailable()) {
                            switch (menuButton.getId()) {
                                case 0:
                                    keyboardManager.setShouldGamePause(false);
                                    break;
                                case 1:
                                    core.takeBack();
                                    break;
                                case 2:
                                    core.init();
                                    keyboardManager.setShouldGamePause(false);
                                    break;
                                case 3:
                                    return false;
                            }
                        }
                        setShouldRerender(true);
                    }
                } else if (menuButton.isHovering() || menuButton.isPushing()) {
                    setShouldRerender(true);
                    menuButton.setHovering(false);
                    menuButton.setPushing(false);
                }
            }
        }
        return true;
    }
}
