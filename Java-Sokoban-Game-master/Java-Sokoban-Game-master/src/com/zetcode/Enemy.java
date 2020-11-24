package com.zetcode;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Enemy extends Actor {

	private static final long serialVersionUID = 1L;
	
	public int dir = -1;
	public int lastDir = -1;
	
	public Enemy(int x, int y) {
        super(x, y);

        initEnemy();
    }
	
	private void initEnemy() {
		ImageIcon iicon = new ImageIcon("src/resources/enemy.png");
        Image image = iicon.getImage();
        setImage(image);
	}
	
	public void move(int x, int y) {

        int dx = x() + x;
        int dy = y() + y;
        
        setX(dx);
        setY(dy);
    }
	
}
