package com.zetcode;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Score {
	File scoreboard;
	
	int score;
	String fileName;
	private ArrayList<Integer> scores = new ArrayList<>();
	
	public Score() {
		this.score = 0;
	}
	
	public void save(int score) {
		scores.add(score);
		BufferedWriter bw = null;
		fileName = "C:\\Users\\USER\\eclipse-workspace\\scoreboard.txt";
		scoreboard = new File(fileName);
		try {
			bw = new BufferedWriter(new FileWriter(fileName,true));
			for(int i=0;i<scores.size();i++) {
				bw.write("Score: "+scores.get(i));
				bw.newLine();
			}
			bw.flush();
			bw.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	public void UpdateScore(int score) {
		this.score = score;
	}
	
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.setFont(new Font("³ª´®°íµñ",Font.PLAIN,22));
		g.drawString("Coin: ", 500, 130);
		g.drawString(String.valueOf(score), 640, 130);
	}
}
