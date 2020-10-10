package com.zetcode;

import java.awt.Container;

import javax.swing.JFrame;

public class GameOver extends JFrame {
	private static final long serialVersionUID = 1L;
	
	GameOverPanel gameoverpanel;
	Container contentpane;
	
	ViewController2 controller;
	
	public GameOver() {
        controller = new ViewController2(this);
        
	}
}
