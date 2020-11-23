package com.zetcode;

import java.io.Serializable;

public class SavedState implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	Board board;
	
	public SavedState(Board board) {
		this.board = board;
	}
}
