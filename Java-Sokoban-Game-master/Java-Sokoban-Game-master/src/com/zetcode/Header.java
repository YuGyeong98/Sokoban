package com.zetcode;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.Serializable;

public class Header implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
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
		g.drawString("Level: ", 900, 60);
		g.drawString(Integer.toString(level), 1040, 60);		
		
		//ÀÌµ¿ È½¼ö
		g.setFont(new Font("³ª´®°íµñ",Font.PLAIN,22));
		g.setColor(Color.WHITE);
		g.drawString("Steps: ", 900, 120);
		g.drawString(Integer.toString(steps), 1040, 120);
	}
	
	public void UpdateSteps(int steps) {
		this.steps = steps;
	}
	
	public void UpdateLevels(int level) {
		this.level = level + 1;
	}
	
	
}
