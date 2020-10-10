package com.zetcode;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameOverKeyEvent implements KeyListener{
	GameOver gameover;
	GameOverPanel gameoverpanel;
	
	public GameOverKeyEvent(GameOverPanel gameoverpanel) {
		this.gameoverpanel = gameoverpanel;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode()==10) {//ฟฃลอ
			gameoverpanel.gameOver();
		}
		gameoverpanel.repaint();
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
