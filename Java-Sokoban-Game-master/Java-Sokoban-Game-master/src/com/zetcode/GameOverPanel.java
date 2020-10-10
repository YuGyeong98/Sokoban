package com.zetcode;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class GameOverPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	ViewController2 controller;
	GameOverPanel gameoverpanel;
	
	public GameOverPanel(ViewController2 controller) {
		this.controller = controller;
		this.addKeyListener(new GameOverKeyEvent(this));
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setFont(new Font("DOSMyungjo",Font.BOLD,40));
		g.setColor(Color.YELLOW);
		g.drawString("Game Over", 270,120);
		g.setFont(new Font("DOSMyungjo",Font.PLAIN,20));
		g.drawString(">> Press enter to restart game <<", 100, 150);
		
	}
	
	public void gameOver() {
		controller.change();
	}
}
