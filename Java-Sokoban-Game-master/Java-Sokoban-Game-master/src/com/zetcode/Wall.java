package com.zetcode;

import java.awt.Image;
import javax.swing.ImageIcon;

public class Wall extends Actor {//Actor클래스를 상속받는다.

	private static final long serialVersionUID = 1L;
	
	private Image image;

    public Wall(int x, int y) {
        super(x, y);
        
        initWall();
    }
    
    private void initWall() {//벽 이미지 로드
        
        ImageIcon iicon = new ImageIcon("src/resources/wall.png");
        image = iicon.getImage();
        setImage(image);
    }
}
