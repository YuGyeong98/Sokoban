package com.zetcode;

import java.awt.Container;

public class ViewController {
	Intro intro;
	Container contentPane;
	IntroPanel introPanel;
	
	public ViewController(Intro intro) {
		this.intro = intro;
		init();
	}
	
	private void init() {
		introPanel = new IntroPanel(this);
		contentPane = intro.getContentPane();
		contentPane.add(introPanel);
		introPanel.requestFocus();
	}
	
	public void change() {
		contentPane.remove(introPanel);
		contentPane.add(new Board());
		intro.setVisible(false);
		intro.setVisible(true);
	}
}
