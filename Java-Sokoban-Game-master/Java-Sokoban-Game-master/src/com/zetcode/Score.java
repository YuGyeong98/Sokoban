package com.zetcode;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

public class Score {
	String path;
	File scoreboard;
	private ArrayList<Integer> scores;
	
	public int score;
	private int temp;
	public int total;
	public int rank;
	
	public Score(String path){
		this.path = path;
		scores = new ArrayList<>();
		score = 0;
		scoreboard = new File(path);
		try {//���� ���� ��� scores(ArrayList)�� ����
			BufferedWriter bw = new BufferedWriter(new FileWriter(path));
			bw.write(String.valueOf(score));
			bw.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void insertScore() {//player�� ���� ���� ������ �߰��Ͽ� �������� ����
		scores.add(this.score);
		Collections.sort(scores);
		total = scores.size();
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(path));
			PrintWriter pw = new PrintWriter(bw);
			for(int i=total-1;i>=0;i--) {
				temp = scores.get(i);
				pw.printf("%d\n", temp);
				if(score==temp) {
					rank = total-i;
				}
			}
			pw.close();
			bw.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.setFont(new Font("�������",Font.PLAIN,22));
		g.drawString("Score: ", 500, 130);
		g.drawString(String.valueOf(score),640,130);
	}
}
