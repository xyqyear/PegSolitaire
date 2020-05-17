package com.xyqyear.pegsolitaire.gui;

import com.jhlabs.image.BoxBlurFilter;
import com.xyqyear.pegsolitaire.core.Core;
import com.xyqyear.pegsolitaire.core.Position;
import com.xyqyear.pegsolitaire.core.State;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Renderer {
    // singleton stuff
    private static Renderer singletonRenderer;
    private Renderer(){
        init();
    }
    public static Renderer getInstance() {
        if (singletonRenderer == null)
            singletonRenderer = new Renderer();
        return singletonRenderer;
    }

    private Core core = Core.getInstance();
    private Game game = Game.getInstance();
    private MouseManager mouseManager = MouseManager.getInstance();
    private RecordManager recordManager = RecordManager.getInstance();

    private BufferedImage boardImage;
    private BufferedImage pieceImage;
    private BufferedImage buttonImage;
    private BufferedImage pushedButtonImage;

    private BufferedImage frame = Utils.create(Config.CANVAS_WIDTH, Config.CANVAS_HEIGHT, false);
    private int blurredIteration = 20;

    private boolean isFirstRenderHoldingPiece = true;

    private void init() {
        try {
            boardImage = ImageIO.read(getClass().getResource("/assets/Board.png"));
            pieceImage = ImageIO.read(getClass().getResource("/assets/Piece.png"));
            buttonImage = ImageIO.read(getClass().getResource("/assets/Button.png"));
            pushedButtonImage = ImageIO.read(getClass().getResource("/assets/ButtonPushed.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void renderGame(Graphics2D g) {
        if (game.shouldRerender()) {
            game.setShouldRerender(false);

            // graphics properties
            Graphics2D frameG = (Graphics2D) frame.getGraphics();
            frameG.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            frameG.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

            // draw board and pieces
            frameG.drawImage(boardImage, 0, 0,null);
            Position fromPos = game.getFromPos();
            Position currentPos = new Position();
            for (int x = 0; x < 7; x++) {
                for (int y = 0; y < 7; y++) {
                    if (core.getBoard().getPiece(x, y) == State.EXIST) {
                        if (fromPos == null || !fromPos.equal(x, y)) {
                            currentPos.setPosition(Utils.piecePos2ScreenPos(x, y));
                            frameG.drawImage(pieceImage, currentPos.getX(), currentPos.getY(), null);
                        }
                    }
                }
            }

            if (fromPos != null) {
                game.setShouldRerender(true);
                currentPos.setPosition(mouseManager.getMousePos());
                if (isFirstRenderHoldingPiece) {
                    isFirstRenderHoldingPiece = false;
                    Position pieceScreenPos = Utils.piecePos2ScreenPos(fromPos.getX(), fromPos.getY());
                    // the magic number 5 for the piece amplification
                    game.setPieceRenderOffset(pieceScreenPos.getX() - currentPos.getX() - 5, pieceScreenPos.getY() - currentPos.getY() - 5);
                }
                currentPos.move(game.getPieceRenderOffset());
                frameG.drawImage(pieceImage, currentPos.getX(), currentPos.getY(), 70, 70, null);
            } else {
                isFirstRenderHoldingPiece = true;
            }

            if (game.getState() == 0) {
                // render blurred background
                if (blurredIteration < 20) {
                    blurredIteration++;
                    game.setShouldRerender(true);
                }
                BufferedImage blurredFrame = new BoxBlurFilter(blurredIteration / 2, blurredIteration / 2, 1).filter(frame, null);
                Utils.copyImage(blurredFrame, frame);

                // render the buttons
                if  (blurredIteration >= 20) {
                    // game title
                    frameG.setFont(new Font(Config.TITLE_FONT, Font.PLAIN, Config.TITLE_SIZE));
                    frameG.setColor(Color.black);
                    frameG.drawString("孔明棋", Config.TITLE_X, Config.TITLE_Y);

                    // game over
                    if (core.isFinished()) {
                        frameG.setFont(new Font(Config.GAMEOVER_FONT, Font.PLAIN, Config.GAMEOVER_SIZE));
                        frameG.setColor(Color.red);
                        frameG.drawString("游戏结束", Config.GAMEOVER_X, Config.GAMEOVER_Y);
                    }

                    // record
                    frameG.setFont(new Font(Config.HIGHSCORE_FONT, Font.PLAIN, Config.HIGHSCORE_SIZE));
                    frameG.setColor(Color.black);
                    int highScore = recordManager.getHighScore();
                    if (highScore == 0) {
                        frameG.drawString("无游戏记录", Config.HIGHSCORE_X, Config.HIGHSCORE_Y);
                    } else {
                        frameG.drawString("最少记录:", Config.HIGHSCORE_X, Config.HIGHSCORE_Y);
                        frameG.setColor(Color.red);
                        frameG.drawString(Integer.toString(highScore), Config.HIGHSCORE_INT_X, Config.HIGHSCORE_INT_Y);
                    }

                    // buttons
                    for (MenuButton menuButton : game.getMenuButtons()) {
                        if (!menuButton.isAvailable()) {
                            frameG.drawImage(pushedButtonImage, menuButton.getX(), menuButton.getY(), null);
                            frameG.setFont(new Font(Config.BUTTON_FONT, Font.PLAIN, Config.BUTTON_SIZE));
                            frameG.setColor(Color.darkGray);
                            frameG.drawString( menuButton.getButtonString(), menuButton.getX()+32, menuButton.getY()+40);
                        }
                        else if (menuButton.isPushing()) {
                            frameG.drawImage(pushedButtonImage, menuButton.getX(), menuButton.getY(), null);
                            frameG.setFont(new Font(Config.BUTTON_FONT, Font.PLAIN, Config.BUTTON_SIZE));
                            frameG.setColor(Color.black);
                            frameG.drawString( menuButton.getButtonString(), menuButton.getX()+32, menuButton.getY()+40);
                        }
                        else if (menuButton.isHovering()) {
                            frameG.drawImage(buttonImage, menuButton.getX() - 5, menuButton.getY() - 5, 240 + 10, 70 + 10, null);
                            frameG.setFont(new Font(Config.BUTTON_FONT, Font.PLAIN, Config.BUTTON_HOVERING_SIZE));
                            frameG.setColor(Color.black);
                            frameG.drawString( menuButton.getButtonString(), menuButton.getX()+20, menuButton.getY()+40);
                        }
                        else {
                            frameG.drawImage(buttonImage, menuButton.getX(), menuButton.getY(), null);
                            frameG.setFont(new Font(Config.BUTTON_FONT, Font.PLAIN, Config.BUTTON_SIZE));
                            frameG.setColor(Color.black);
                            frameG.drawString( menuButton.getButtonString(), menuButton.getX()+32, menuButton.getY()+40);
                        }
                    }
                }

            } else if (blurredIteration > 0) {
                blurredIteration = 0;
            }
            frameG.dispose();
        }
        g.drawImage(frame, 0, 0, null);
    }
}
