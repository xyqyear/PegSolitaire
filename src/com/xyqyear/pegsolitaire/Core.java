package com.xyqyear.pegsolitaire;

import java.util.ArrayList;

public class Core {
    private final Board initBoard = new Board();
    private Board board = new Board();
    private ArrayList<Board> boardHistory = new ArrayList<>();

    public void init() {
        board.update(initBoard);
        boardHistory.clear();
    }

    public boolean canDoStep(int fromX, int fromY, int toX, int toY) {
        return false;
    }

    public boolean doStep(int fromX, int fromY, int toX, int toY) {
        return false;
    }

    public void takeBack() {

    }

    public boolean isFinished() {
        return false;
    }

    public Board getBoard() {return board; }
}
