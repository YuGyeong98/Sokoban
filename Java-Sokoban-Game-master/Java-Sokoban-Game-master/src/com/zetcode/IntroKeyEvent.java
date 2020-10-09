package com.zetcode;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class IntroKeyEvent implements KeyListener{
	Intro intro;
	IntroPanel intropanel;
	
	public IntroKeyEvent(IntroPanel intropanel) {
		this.intropanel = intropanel;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode()==38) {//����Ű ����
			intropanel.select = 0;
		}
		else if(e.getKeyCode()==40) {//�Ʒ���
			intropanel.select = 1;
		}
		else if(e.getKeyCode()==10) {//����
			if(intropanel.select==0) {
				intropanel.gameStart();
			}
			else if(intropanel.select==1) {
				
			}
		}
		intropanel.repaint();
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
}
