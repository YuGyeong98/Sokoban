package com.zetcode;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Player extends Actor{//Actor클래스를 상속받는다
	
    public Player(int x, int y) {
        super(x, y);
        initPlayer(); 
    }

	private void initPlayer() {
        ImageIcon iicon = new ImageIcon("src/resources/sokoban.png");
        Image image = iicon.getImage();
        setImage(image);
    }

    public void move(int x, int y) {//게임안에서 물체를 움직인다.
    	
        int dx = x() + x;
        int dy = y() + y;
        
        setX(dx);
        setY(dy);
    }
    
    
}
