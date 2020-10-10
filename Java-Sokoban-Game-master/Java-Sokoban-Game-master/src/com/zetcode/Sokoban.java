package com.zetcode;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class Sokoban extends JFrame {//main Å¬·¡½º
	 
	private static final long serialVersionUID = 1L;

    public Sokoban() {
        initUI();
    }
    
    private void initUI() {
        Board board = new Board();
        add(board);
        GameOver gameover = new GameOver();
        add(gameover);
    }
    
    public static void main(String[] args) { 
    	EventQueue.invokeLater(() -> {
            Intro intro = new Intro();
            intro.setVisible(true);
        });
    }
}
