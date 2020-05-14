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
    private Mouse mouse = Mouse.getInstance();

    private BufferedImage boardImage;
    private BufferedImage pieceImage;
    private BufferedImage buttonImage;
    private BufferedImage pushedButtonImage;

    private BufferedImage blurredImage;
    private int blurredIteration = 0;

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

    public void renderGame(Graphics2D g, BufferedImage bg) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        g.drawImage(boardImage, 0, 0,null);
        Position fromPos = game.getFromPos();
        Position currentPos = new Position();

        for (int x = 0; x < 7; x++) {
            for (int y = 0; y < 7; y++) {
                if (core.getBoard().getPiece(x, y) == State.EXIST) {
                    if (fromPos == null || !fromPos.equal(x, y)) {
                        currentPos.setPosition(Utils.piecePos2ScreenPos(x, y));
                        g.drawImage(pieceImage, currentPos.getX(), currentPos.getY(), null);
                    }
                }
            }
        }

        if (fromPos != null) {
            currentPos.setPosition(mouse.getMousePos());
            if (isFirstRenderHoldingPiece) {
                isFirstRenderHoldingPiece = false;
                Position pieceScreenPos = Utils.piecePos2ScreenPos(fromPos.getX(), fromPos.getY());
                // the magic number 5 for the piece amplification
                game.setPieceRenderOffset(pieceScreenPos.getX() - currentPos.getX() - 5, pieceScreenPos.getY() - currentPos.getY() - 5);
            }
            currentPos.move(game.getPieceRenderOffset());
            g.drawImage(pieceImage, currentPos.getX(), currentPos.getY(), 70, 70, null);
        } else {
            isFirstRenderHoldingPiece = true;
        }

        if (game.getState() == 0) {
            // render blurred background
            if (blurredIteration < 20) {
                blurredIteration++;
                blurredImage = new BoxBlurFilter(blurredIteration / 2, blurredIteration / 2, 1).filter(bg, null);
            }
            Utils.copyImage(blurredImage, bg);

            // render the buttons
            if  (blurredIteration >= 20) {
                for (MenuButton menuButton : game.getMenuButtons()) {
                    if (menuButton.isPushing())
                        g.drawImage(pushedButtonImage, menuButton.getPosition().getX(), menuButton.getPosition().getY(), null);
                    else if (menuButton.isHovering())
                        g.drawImage(buttonImage, menuButton.getPosition().getX()-5, menuButton.getPosition().getY()-5, 240 + 10, 70 + 10, null);
                    else
                        g.drawImage(buttonImage, menuButton.getPosition().getX(), menuButton.getPosition().getY(), null);
                }
            }

        } else if (blurredIteration > 0)
            blurredIteration = 0;
    }
}
