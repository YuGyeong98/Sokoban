package com.zetcode;
import java.io.Serializable;

/*
 * This class creates the HighScores object which stores a score (# of movements) and name 
 */
@SuppressWarnings("serial")
public class HighScores implements Serializable {

	private int score;
	private String name;
	
	public int getScore() {
		return score;
	}
	
	public String getName() {
		return name;
	}
	
	public HighScores(String name, int score) {
		this.score = score;
		this.name = name;
	}
	
}
