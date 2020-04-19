package com.xyqyear.pegsolitaire;

public class Board {
    private State[][] board = {
            {State.DISABLED, State.DISABLED, State.EXIST, State.EXIST, State.EXIST, State.DISABLED, State.DISABLED},
            {State.DISABLED, State.DISABLED, State.EXIST, State.EXIST, State.EXIST, State.DISABLED, State.DISABLED},
            {State.EXIST,    State.EXIST,    State.EXIST, State.EXIST, State.EXIST, State.EXIST,    State.EXIST},
            {State.EXIST,    State.EXIST,    State.EXIST, State.TAKEN, State.EXIST, State.EXIST,    State.EXIST},
            {State.EXIST,    State.EXIST,    State.EXIST, State.EXIST, State.EXIST, State.EXIST,    State.EXIST},
            {State.DISABLED, State.DISABLED, State.EXIST, State.EXIST, State.EXIST, State.DISABLED, State.DISABLED},
            {State.DISABLED, State.DISABLED, State.EXIST, State.EXIST, State.EXIST, State.DISABLED, State.DISABLED},
    };
    private int pieceNum = 32;

    public State getPiece(int x, int y) {
        return board[y][x];
    }

    // 设置某一个位置的棋子为给定状态
    // 注意： 要修改pieceNum， 比如这个位置本来有棋子，后来没棋子，pieceNum就减一
    public void setPiece(int x, int y, State state) {
    }

    // 将参数的棋盘复制到自己里面，包含棋子和pieceNum
    // 注意： 数组的复制要用for循环深拷贝
    public void update(Board board) {

    }

    // 获取棋子的个数
    public int getPieceNum() {
        return pieceNum;
    }
}
