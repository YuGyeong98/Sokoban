package com.zetcode;
import java.io.Serializable;

/*
 * This class creates the HighScores object which stores a score (# of movements)
 */
@SuppressWarnings("serial")
public class HighScores implements Serializable {

	private int score;
	
	public HighScores(){
		
	}
	
	public int getScore() {
		return score;
	}

	
	public HighScores(int score) {
		this.score = score;
	}
	
}

