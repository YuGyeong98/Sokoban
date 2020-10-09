package com.zetcode;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

public class IntroPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	ViewController controller;
	IntroPanel intropanel;
	
	int select = 0;
	
	public IntroPanel(ViewController controller) {
		this.controller = controller;
		this.addKeyListener(new IntroKeyEvent(this));
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setFont(new Font("DOSMyungjo",Font.BOLD,40));
		g.setColor(Color.YELLOW);
		g.drawString("Sokoban", 270,130);
		g.setFont(new Font("DOSMyungjo",Font.PLAIN,25));
		g.drawString("New Game", 293, 260);
		g.drawString("Continue", 292, 310);
		
		if(select==0) {
			g.drawString(">>", 260, 260);
			g.drawString("<<", 400, 260);
		}
		else {
			g.drawString(">>", 260, 310);
			g.drawString("<<", 400, 310);
		}
	}
	public void gameStart() {
		controller.change();
	}

}
