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
        return board[x][y];
    }

    public State setPiece(int x, int y) {
        return board[x][y];
    }

    public void update(Board board) {

    }

    public int getPieceNum() {
        return pieceNum;
    }
}
