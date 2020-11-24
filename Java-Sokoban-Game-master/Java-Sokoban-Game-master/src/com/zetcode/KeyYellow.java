package com.zetcode;

import java.awt.Image;

import javax.swing.ImageIcon;

public class KeyYellow extends Actor{

	private static final long serialVersionUID = 1L;
	
	private Image image;
	
	public KeyYellow(int x, int y) {
		super(x, y);
		initKey();
	}

	private void initKey() {
		ImageIcon iicon = new ImageIcon("src/resources/keyYellow.png");
		image = iicon.getImage();
		setImage(image);
	}
}
