package com.zetcode;

import java.awt.Container;

public class ViewController2 {
	GameOver gameover;
	Container contentPane;
	GameOverPanel gameoverpanel;
	
	public ViewController2(GameOver gameover) {
		this.gameover = gameover;
		init();
	}
	
	private void init() {
		gameoverpanel = new GameOverPanel(this);
		contentPane = gameover.getContentPane();
		contentPane.add(gameoverpanel);
		gameoverpanel.requestFocus();
	}
	
	public void change() {
		contentPane.remove(gameoverpanel);
		contentPane.add(new Board());
		gameoverpanel.setVisible(false);
		gameoverpanel.setVisible(true);
	}
}
