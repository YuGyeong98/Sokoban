package com.zetcode;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Timer {
	
	Board board = new Board();
	String timerBuffer;//경과 시간 문자열이 저장될 버퍼
	int startTime;//타이머가 on되었을 때 시간을 기억하고 있는 변수
	
	public Timer() {
		timer(1);//1을 인수로 넣으면 타이머가 켜짐
		pause();//유저가 박스를 다 옮길때까지 무한정 대기
		timer(0);//0을 넣으면 타이머가 꺼지고 시간 문자열을 버퍼에 설정
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
		g.setFont(new Font("나눔고딕",Font.PLAIN,22));
		g.setColor(Color.WHITE);
		g.drawString("Time: ", 100, 30);
		g.drawString(timerBuffer, 170, 30);
	}
}
