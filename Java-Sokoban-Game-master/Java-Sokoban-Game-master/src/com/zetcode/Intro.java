package com.zetcode;

import java.awt.Container;

import javax.swing.JFrame;

public class Intro extends JFrame{
	private static final long serialVersionUID = 1L;//게임 시작 화면(new game, continue)
	
	IntroPanel intropanel;
	Container contentpane;
	
	ViewController controller;
	
	Board board = new Board();
	public Intro() {
		setTitle("Sokoban");
		setSize(board.getBoardWidth() + 35, board.getBoardHeight() + 2 * 35);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);//프레임을 화면 가운데에 배치
        setVisible(true);
        controller = new ViewController(this);
	}
}
