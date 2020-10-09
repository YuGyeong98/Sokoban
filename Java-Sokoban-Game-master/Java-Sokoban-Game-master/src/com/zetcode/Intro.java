package com.zetcode;

import java.awt.Container;

import javax.swing.JFrame;

public class Intro extends JFrame{
	private static final long serialVersionUID = 1L;//���� ���� ȭ��(new game, continue)
	
	IntroPanel intropanel;
	Container contentpane;
	
	ViewController controller;
	
	Board board = new Board();
	public Intro() {
		setTitle("Sokoban");
		setSize(board.getBoardWidth() + 35, board.getBoardHeight() + 2 * 35);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);//�������� ȭ�� ����� ��ġ
        setVisible(true);
        controller = new ViewController(this);
	}
}
