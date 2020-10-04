package com.zetcode;

import java.awt.Image;
import javax.swing.ImageIcon;

public class Wall extends Actor {//ActorŬ������ ��ӹ޴´�.

    private Image image;

    public Wall(int x, int y) {
        super(x, y);
        
        initWall();
    }
    
    private void initWall() {//�� �̹��� �ε�
        
        ImageIcon iicon = new ImageIcon("src/resources/wall.png");
        image = iicon.getImage();
        setImage(image);
    }
}