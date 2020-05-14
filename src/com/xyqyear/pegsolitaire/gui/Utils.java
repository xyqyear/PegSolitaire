package com.xyqyear.pegsolitaire.gui;

import com.xyqyear.pegsolitaire.core.Core;
import com.xyqyear.pegsolitaire.core.Position;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Utils {
    public static Position piecePos2ScreenPos(int x, int y) {
        return new Position(79 + x * 65, 80 + y * 66);
    }

    public static Position piecePos2ScreenPos(Position position) {
        return piecePos2ScreenPos(position.getX(), position.getY());
    }

    public static Position screenPos2PiecePos(int x, int y) {
        Position pos = new Position((x - 79) / 65, (y - 80) / 66);
        if (Core.getInstance().isInBoard(pos))
            return pos;
        else
            return null;
    }

    public static Position screenPos2PiecePos(Position position) {
        return screenPos2PiecePos(position.getX(), position.getY());
    }

    public static BufferedImage copyImage(BufferedImage source, BufferedImage destination){
        if (destination == null) {
            destination = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
        }
        Graphics g = destination.getGraphics();
        g.drawImage(source, 0, 0, null);
        g.dispose();
        return destination;
    }

    public static boolean inRange(Position target, Position start, Position range) {
        return     target.getX() >= start.getX()
                && target.getX() <= start.getX() + range.getX()
                && target.getY() >= start.getY()
                && target.getY() <= start.getY() + range.getY();
    }
}
