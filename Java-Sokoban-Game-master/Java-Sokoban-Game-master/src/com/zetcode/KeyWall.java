package com.zetcode;

import java.awt.Image;

import javax.swing.ImageIcon;

public class KeyWall extends Actor {
	
	private static final long serialVersionUID = 1L;
	
	private Image image;
	
	public KeyWall(int x, int y) {
		super(x, y);
		initKeyWall();
	}

	private void initKeyWall() {
		ImageIcon iicon = new ImageIcon("src/resources/keywall.png");
		image = iicon.getImage();
		setImage(image);
	}
	
}