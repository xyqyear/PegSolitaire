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

    // 判断这样子走棋是否非法
    public boolean canDoStep(int fromX, int fromY, int toX, int toY) {
        return false;
    }

    // 实质上走棋
    // 先用canDoStep方法考虑是否可以走，如果不能走直接返回false
    // 如果可以走那么就先在历史棋盘里添加一个board对象，把当前的棋盘复制进去，方便悔棋
    // 然后再利用孔明棋的规则走棋
    // 注: 设置棋子和得到棋子是使用board.setPiece()和board.getPiece()方法
    public boolean doStep(int fromX, int fromY, int toX, int toY) {

        return false;
    }

    // 悔棋方法，从历史棋盘里面取出上一步的棋盘，复制到当前棋盘中
    public void takeBack() {

    }

    // 判断游戏是否结束
    public boolean isFinished() {
        return false;
    }

    public Board getBoard() {return board; }
}
