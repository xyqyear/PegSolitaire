package com.xyqyear.pegsolitaire.core;

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

    public Board() {
        super();
    }

    public Board(Board board) {
        this.update(board);
    }

    public State getPiece(int x, int y) {
    	
        return board[y][x];
    }

    public State getPiece(Position position) {
        return getPiece(position.getX(), position.getY());
    }

    public void setPiece(int x, int y, State state) {
    	if (board[y][x] == State.EXIST && state == State.TAKEN)
    		this.pieceNum--;
    	else if(board[y][x] == State.TAKEN && state == State.EXIST)
    		this.pieceNum++;
        this.board[y][x] = state;
    }

    public void setPiece(Position position, State state) {
        setPiece(position.getX(), position.getY(), state);
    }

    public void update(Board board) {
    	for(int i = 0; i < 7; i++) {
    		for(int j = 0; j < 7; j++) {
    			this.board[j][i] = board.getPiece(i,j);
    		}
    	}
    	this.pieceNum = board.getPieceNum();
    }

    public int getPieceNum() {
        return pieceNum;
    }
}
