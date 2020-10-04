package com.zetcode;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class Sokoban extends JFrame {//main 클래스
	 
	private static final long serialVersionUID = 1L;

	private final int OFFSET = 30;

    public Sokoban() {

        initUI();
    }

    private void initUI() {
        
        Board board = new Board();
        add(board);
      
        setTitle("Sokoban");
        
        setSize(board.getBoardWidth() + OFFSET, board.getBoardHeight() + 2 * OFFSET);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
       
    }
    
    public static void main(String[] args) {
    	//Swing컴포넌트로의 모든 접근이 단일 쓰레드에서만 이루어져야한다 = event-dispatch thread
    	//event-dispatch thread 의 태스크를 실행해야하지만 결과값이 필요하지않고 태스크가 언제 끝나던 상관없다면, EventQueue의 public static void invokeLater(Runnable runnable) 메소드를 사용 
        EventQueue.invokeLater(() -> {
            Sokoban game = new Sokoban();
            game.setVisible(true);
        });
    }
}
