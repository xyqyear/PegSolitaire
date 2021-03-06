package com.xyqyear.pegsolitaire.core;

import java.util.ArrayList;

public class Core {
    private static Core singletonCore;
    private Core() {
        this.init();
    }
    public static Core getInstance() {
        if (singletonCore == null)
            singletonCore = new Core();
        return singletonCore;
    }

    private final Board initBoard = new Board();
    private Board board = new Board();
    private ArrayList<Board> boardHistory = new ArrayList<>();

    private boolean finished;

    public void init() {
        board.update(initBoard);
        boardHistory.clear();
        finished = false;
    }

    public Position getMiddle(Position from, Position to) {
        if (from.getX() == to.getX() && Math.abs(from.getY() - to.getY()) == 2) {
            return new Position(to.getX(), (from.getY() + to.getY()) / 2);
        } else if (from.getY() == to.getY() && Math.abs(from.getX() - to.getX()) == 2) {
            return new Position((from.getX() + to.getX()) / 2, to.getY());
        }

        return null;
    }

    public boolean isInBoard(Position pos) {
        return pos != null && (pos.getX() >= 0 && pos.getX() < 7 && pos.getY() >= 0 && pos.getY() < 7)
                && ((pos.getX() > 1 && pos.getX() < 5) || (pos.getY() > 1 && pos.getY() < 5));
    }

    public boolean canDoStep(Position from, Position to) {
        if (!(isInBoard(from) && isInBoard(to)))
            return false;
        Position middlePos = getMiddle(from, to);
        if (middlePos != null)
            return board.getPiece(from.getX(), from.getY()) == State.EXIST &&
                   board.getPiece(middlePos.getX(), middlePos.getY()) == State.EXIST &&
                   board.getPiece(to.getX(), to.getY()) == State.TAKEN;

        return false;
    }

    public boolean doStep(Position from, Position to) {
        if (!canDoStep(from, to))
            return false;
        Board historyBoard = new Board(board);
        this.boardHistory.add(historyBoard);

        Position middlePos = getMiddle(from, to);
        board.setPiece(from.getX(), from.getY(), State.TAKEN);
        board.setPiece(middlePos.getX(), middlePos.getY(), State.TAKEN);
        board.setPiece(to.getX(), to.getY(), State.EXIST);

        return true;
    }

    public void takeBack() {
        if (finished)
            finished = false;

        this.board.update(boardHistory.get(boardHistory.size()-1));
        boardHistory.remove(boardHistory.size()-1);
    }

    public boolean isFinished() {
        if (finished)
            return true;

        for (int y = 0; y < 7; y++) {
            for (int x = 0; x < 7; x++) {
                Position from = new Position(x, y);
                if (isInBoard(from)) {
                    for (int offsetY = -2; offsetY <= 2; offsetY += 4) {
                        int toY = y + offsetY;
                        Position to = new Position(x, toY);

                        if (isInBoard(to)) {
                            if (canDoStep(from, to)) {
                                return false;
                            }
                        }
                    }

                    for (int offsetX = -2; offsetX <= 2; offsetX += 4) {
                        int toX = x + offsetX;
                        Position to = new Position(toX, y);

                        if (isInBoard(to)) {
                            if (canDoStep(from, to)) {
                                return false;
                            }
                        }
                    }
                }
            }
        }

        finished = true;
        return true;
    }

    public Board getBoard() {return board; }
}
