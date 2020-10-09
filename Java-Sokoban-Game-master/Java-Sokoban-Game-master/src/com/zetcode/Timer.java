package com.zetcode;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Timer {
	
	Board board = new Board();
	String timerBuffer;//��� �ð� ���ڿ��� ����� ����
	int startTime;//Ÿ�̸Ӱ� on�Ǿ��� �� �ð��� ����ϰ� �ִ� ����
	
	public Timer() {
		timer(1);//1�� �μ��� ������ Ÿ�̸Ӱ� ����
		pause();//������ �ڽ��� �� �ű涧���� ������ ���
		timer(0);//0�� ������ Ÿ�̸Ӱ� ������ �ð� ���ڿ��� ���ۿ� ����
	}
	
	public void timer(int onoff) {
		if(onoff==1) {
			startTime = (int) (System.currentTimeMillis()/1000);
		}
		if(onoff==0) {
			secToHHMMSS(((int)System.currentTimeMillis()/1000) - startTime);
		}
	}
	
	public void secToHHMMSS(int secs) {
		int hour,min,sec;
		sec = secs%60;
		min = (secs/60)%60;
		hour = secs/3600;
		
		timerBuffer = String.format("%02d:%02d:%02d",hour,min,sec);		
	}
	public void pause() {
		board.isCompleted();
	}
	
	public void render(Graphics g) {
		g.setFont(new Font("�������",Font.PLAIN,22));
		g.setColor(Color.WHITE);
		g.drawString("Time: ", 100, 30);
		g.drawString(timerBuffer, 170, 30);
	}
}
