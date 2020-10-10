package com.zetcode;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Header{
	private int steps;
	private int level;
	
	public Header() {
		this.steps = 0;
		this.level = 1;
	}
	
	public void render(Graphics g) {
		//·¹º§
		g.setFont(new Font("³ª´®°íµñ",Font.PLAIN,22));
		g.setColor(Color.WHITE);
		g.drawString("Level: ", 500, 40);
		g.drawString(Integer.toString(level), 640, 40);		
		
		//ÀÌµ¿ È½¼ö
		g.setFont(new Font("³ª´®°íµñ",Font.PLAIN,22));
		g.setColor(Color.WHITE);
		g.drawString("Steps: ", 500, 100);
		g.drawString(Integer.toString(steps), 640, 100);
	}
	
	public void UpdateSteps(int i) {
		this.steps = i;
	}
	
}
