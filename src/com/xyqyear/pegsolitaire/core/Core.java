package com.xyqyear.pegsolitaire;

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


    public void init() {
        board.update(initBoard);
        boardHistory.clear();
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
        return (pos.getX() >= 0 && pos.getX() < 7 && pos.getY() >= 0 && pos.getY() < 7)
                && ((pos.getX() > 1 && pos.getX() < 5) || (pos.getY() > 1 && pos.getY() < 5));
    }

    // 判断这样子走棋是否非法
    public boolean canDoStep(Position from, Position to) {
        Position middlePos = getMiddle(from, to);
        if (middlePos != null)
            return board.getPiece(from.getX(), from.getY()) == State.EXIST &&
                   board.getPiece(middlePos.getX(), middlePos.getY()) == State.EXIST &&
                   board.getPiece(to.getX(), to.getY()) == State.TAKEN;

        return false;
    }

    // 实质上走棋
    // 先用canDoStep方法考虑是否可以走，如果不能走直接返回false
    // 如果可以走那么就先在历史棋盘里添加一个board对象，把当前的棋盘复制进去，方便悔棋
    // 然后再利用孔明棋的规则走棋
    // 注: 设置棋子和得到棋子是使用board.setPiece()和board.getPiece()方法
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

    // 悔棋方法，从历史棋盘里面取出上一步的棋盘，复制到当前棋盘中
    public void takeBack() {
        this.board.update(boardHistory.get(boardHistory.size()-1));
        boardHistory.remove(boardHistory.size()-1);
    }

    // 判断游戏是否结束
    public boolean isFinished() {
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

        return true;
    }

    public Board getBoard() {return board; }
}
