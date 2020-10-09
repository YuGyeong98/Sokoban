package com.zetcode;

import java.awt.Image;
import javax.swing.ImageIcon;

public class Coin extends Actor{
	
	private Image image;
	int x,y;
	public Coin(int x, int y) {
		super(x, y);
		initCoin();
	}

	public void initCoin() {
		ImageIcon iicon = new ImageIcon("src/resources/coin.png");
		image = iicon.getImage();
		setImage(image);
	}
	

}
