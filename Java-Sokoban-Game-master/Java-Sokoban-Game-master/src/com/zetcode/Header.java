package com.zetcode;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Header{
	private int steps;
	private int leftboxes;
	private int level;
	
	public Header() {
		this.steps = 0;
		this.leftboxes = 0;
		this.level = 1;
	}
	
	public void render(Graphics g) {
		//����
		g.setFont(new Font("�������",Font.PLAIN,22));
		g.setColor(Color.WHITE);
		g.drawString("Level: ", 500, 40);
		g.drawString(Integer.toString(level), 640, 40);		
		
		//���� ��
		g.setFont(new Font("�������",Font.PLAIN,22));
		g.setColor(Color.WHITE);
		g.drawString("Leftboxes: ", 500, 70);
		g.drawString(Integer.toString(leftboxes), 640, 70);
		
		//�̵� Ƚ��
		g.setFont(new Font("�������",Font.PLAIN,22));
		g.setColor(Color.WHITE);
		g.drawString("Steps: ", 500, 100);
		g.drawString(Integer.toString(steps), 640, 100);
	}
	public void UpdateSteps(int i) {
		this.steps = i;
	}
	public void Updateboxes(int i) {
		this.leftboxes = i;
	}
	
}
