package com.zetcode;

import java.awt.Image;
import javax.swing.ImageIcon;

public class Area extends Actor {//Actor클래스를 상속받는다.
	
	private static final long serialVersionUID = 1L;

	public Area(int x, int y) {
        super(x, y);//super메소드는 부모 클래스의 생성자를 호출할 때 사용된다.
        
        initArea();
    }
    
    private void initArea() {

        ImageIcon iicon = new ImageIcon("src/resources/area.png");
        Image image = iicon.getImage();//Actor클래스에서 가져와서 사용 
        setImage(image);//Actor클래스에서 가져와서 사용 
    }
}
