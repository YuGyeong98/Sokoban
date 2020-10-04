package com.zetcode;

import java.awt.Image;
import javax.swing.ImageIcon;

public class Baggage extends Actor {//Actor클래스를 상속받는다.

    public Baggage(int x, int y) {
        super(x, y);
        
        initBaggage();
    }
    
    private void initBaggage() {
        
        ImageIcon iicon = new ImageIcon("src/resources/baggage.png");
        Image image = iicon.getImage();
        setImage(image);
    }

    public void move(int x, int y) {//움직일 수 있기때문에 move()메소드가 존재한다.
        
        int dx = x() + x;//x() = Actor클래스에서 가져와서 사용
        int dy = y() + y;
        
        setX(dx);
        setY(dy);
    }
}
