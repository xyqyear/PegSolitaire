package com.xyqyear.pegsolitaire.gui;

import com.xyqyear.pegsolitaire.core.Core;
import com.xyqyear.pegsolitaire.core.Position;

import javax.imageio.ImageIO;
import javax.rmi.CORBA.Util;
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

    private Position pieceRenderOffset = new Position(0 ,0);
    private boolean isFirstRenderHoldingPiece = true;

    private void init() {
        try {
            boardImage = ImageIO.read(getClass().getResource("/assets/Board.png"));
            pieceImage = ImageIO.read(getClass().getResource("/assets/Piece.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void renderGame(Graphics2D g) {
        if (game.getState() == 1) {
            g.drawImage(boardImage, 0, 0,null);
            Position fromPos = game.getFromPos();
            Position currentPos = new Position(0, 0);

            for (int x = 0; x < 7; x++) {
                for (int y = 0; y < 7; y++) {
                    if (core.getBoard().getPiece(x, y) == com.xyqyear.pegsolitaire.core.State.EXIST) {
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
                    pieceRenderOffset.setPosition(pieceScreenPos.getX() - currentPos.getX(), pieceScreenPos.getY() - currentPos.getY());
                }
                currentPos.move(pieceRenderOffset);
                g.drawImage(pieceImage, currentPos.getX(), currentPos.getY(), null);
            } else {
                isFirstRenderHoldingPiece = true;
            }
        }
    }
}
