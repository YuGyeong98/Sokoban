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
		//����
		g.setFont(new Font("��������",Font.PLAIN,22));
		g.setColor(Color.WHITE);
		g.drawString("Level: ", 900, 60);
		g.drawString(Integer.toString(level), 1040, 60);		
		
		//�̵� Ƚ��
		g.setFont(new Font("��������",Font.PLAIN,22));
		g.setColor(Color.WHITE);
		g.drawString("Steps: ", 900, 120);
		g.drawString(Integer.toString(steps), 1040, 120);
	}
	
	public void UpdateSteps(int i) {
		this.steps = i;
	}
	
	public void UpdateLevels(int i) {
		this.level = i + 1;
	}
	
	
}
