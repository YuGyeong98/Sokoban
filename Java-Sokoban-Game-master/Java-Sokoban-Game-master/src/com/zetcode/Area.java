package com.zetcode;

import java.awt.Image;
import javax.swing.ImageIcon;

public class Area extends Actor {//ActorŬ������ ��ӹ޴´�.
	//baggage�� �������� ����

    public Area(int x, int y) {
        super(x, y);//super�޼ҵ�� �θ� Ŭ������ �����ڸ� ȣ���� �� ���ȴ�.
        
        initArea();
    }
    
    private void initArea() {

        ImageIcon iicon = new ImageIcon("src/resources/area.png");
        Image image = iicon.getImage();//ActorŬ�������� �����ͼ� ��� 
        setImage(image);//ActorŬ�������� �����ͼ� ��� 
    }
}
