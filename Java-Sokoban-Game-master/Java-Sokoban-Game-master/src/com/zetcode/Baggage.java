package com.zetcode;

import java.awt.Image;
import javax.swing.ImageIcon;

public class Baggage extends Actor {//ActorŬ������ ��ӹ޴´�.

    public Baggage(int x, int y) {
        super(x, y);
        
        initBaggage();
    }
    
    private void initBaggage() {
        
        ImageIcon iicon = new ImageIcon("src/resources/baggage.png");
        Image image = iicon.getImage();
        setImage(image);
    }

    public void move(int x, int y) {//������ �� �ֱ⶧���� move()�޼ҵ尡 �����Ѵ�.
        
        int dx = x() + x;//x() = ActorŬ�������� �����ͼ� ���
        int dy = y() + y;
        
        setX(dx);
        setY(dy);
    }
}
