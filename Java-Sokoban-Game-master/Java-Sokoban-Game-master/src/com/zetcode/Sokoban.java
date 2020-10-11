package com.zetcode;

import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;

public class Sokoban extends JFrame {

	private static final long serialVersionUID = 1L;

	private final int OFFSET = 35;

	private Image screenImage;
	private Graphics screenGraphic;

	private ImageIcon introBackground = new ImageIcon("src/resources/retro.jpg");

	private ImageIcon startButtonBasicImage = new ImageIcon("src/resources/start1.png");
	private ImageIcon startButtonEnteredImage = new ImageIcon("src/resources/start2.png");
	private ImageIcon quitButtonBasicImage = new ImageIcon("src/resources/quit1.png");
	private ImageIcon quitButtonEnteredImage = new ImageIcon("src/resources/quit2.png");

	private ImageIcon stage1 = new ImageIcon("src/resources/stage1.png");
	private ImageIcon stage2 = new ImageIcon("src/resources/stage2.png");
	private ImageIcon stage3 = new ImageIcon("src/resources/stage3.png");
	private ImageIcon stage4 = new ImageIcon("src/resources/stage4.png");
	private ImageIcon stage5 = new ImageIcon("src/resources/stage5.png");

	private JButton quitButton = new JButton(quitButtonBasicImage);
	private JButton startButton = new JButton(startButtonBasicImage);
	private JButton stage1Button = new JButton(stage1);
	private JButton stage2Button = new JButton(stage2);
	private JButton stage3Button = new JButton(stage3);
	private JButton stage4Button = new JButton(stage4);
	private JButton stage5Button = new JButton(stage5);

	JScrollPane scrollPane;

	JLabel counterLabel;

	Timer timer;

	public Board board = new Board();

	int second;

	public Sokoban() {

		initUI();
	}

	private void initUI() {

		setTitle("Sokoban");

		setSize(board.getBoardWidth() + OFFSET, board.getBoardHeight() + 2 * OFFSET);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		// 종료버튼
		quitButton.setBounds(220, 280, 250, 100);
		quitButton.setBorderPainted(false);
		quitButton.setContentAreaFilled(false);
		quitButton.setFocusPainted(false);
		quitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				quitButton.setIcon(quitButtonEnteredImage);
				quitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				quitButton.setIcon(quitButtonBasicImage);
				quitButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
				System.exit(0);
			}
		});
		background.add(quitButton);

		// 시작버튼
		startButton.setBounds(160, 200, 380, 100);
		startButton.setBorderPainted(false);
		startButton.setContentAreaFilled(false);
		startButton.setFocusPainted(false);
		startButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				startButton.setIcon(startButtonEnteredImage);
				startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				startButton.setIcon(startButtonBasicImage);
				startButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				startButton.setVisible(false);
				quitButton.setVisible(false);
				introBackground = new ImageIcon("src/resources/background2.jpg");
				background.add(stage1Button);
				background.add(stage2Button);
				background.add(stage3Button);
				background.add(stage4Button);
				background.add(stage5Button);
			}
		});

		background.add(startButton);

		// 스테이지1 버튼
		stage1Button.setBounds(280, 40, 125, 44);
		stage1Button.setBorderPainted(false);
		stage1Button.setContentAreaFilled(false);
		stage1Button.setFocusPainted(false);
		stage1Button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				stage1Button.setIcon(stage1);
				stage1Button.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				stage1Button.setIcon(stage1);
				stage1Button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				JOptionPane.showMessageDialog(board, "Highscore: \n" + board.sm.getHighscoreString());
				
				
				background.setVisible(false);
				scrollPane.setVisible(false);
				add(board);

				setContentPane(board);
				board.timer.start();
			}
		});

		// 스테이지2 버튼
		stage2Button.setBounds(280, 110, 125, 44);
		stage2Button.setBorderPainted(false);
		stage2Button.setContentAreaFilled(false);
		stage2Button.setFocusPainted(false);
		stage2Button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				stage2Button.setIcon(stage2);
				stage2Button.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				stage2Button.setIcon(stage2);
				stage2Button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				JOptionPane.showMessageDialog(board, "Highscore: \n" + board.sm.getHighscoreString());
				
				
				background.setVisible(false);
				scrollPane.setVisible(false);
				add(board);

				setContentPane(board);
				board.timer.start();
			}
		});

		// 스테이지3 버튼
		stage3Button.setBounds(280, 180, 125, 44);
		stage3Button.setBorderPainted(false);
		stage3Button.setContentAreaFilled(false);
		stage3Button.setFocusPainted(false);
		stage3Button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				stage3Button.setIcon(stage3);
				stage3Button.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				stage3Button.setIcon(stage3);
				stage3Button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				JOptionPane.showMessageDialog(board, "Highscore: \n" + board.sm.getHighscoreString());
				
			
				background.setVisible(false);
				scrollPane.setVisible(false);
				add(board);

				setContentPane(board);
				board.timer.start();
			}
		});

		// 스테이지4 버튼
		stage4Button.setBounds(280, 250, 125, 44);
		stage4Button.setBorderPainted(false);
		stage4Button.setContentAreaFilled(false);
		stage4Button.setFocusPainted(false);
		stage4Button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				stage4Button.setIcon(stage4);
				stage4Button.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				stage4Button.setIcon(stage4);
				stage4Button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				JOptionPane.showMessageDialog(board, "Highscore: \n" + board.sm.getHighscoreString());
				
				
				background.setVisible(false);
				scrollPane.setVisible(false);
				add(board);

				setContentPane(board);
				board.timer.start();
			}
		});

		// 스테이지5 버튼
		stage5Button.setBounds(280, 320, 125, 44);
		stage5Button.setBorderPainted(false);
		stage5Button.setContentAreaFilled(false);
		stage5Button.setFocusPainted(false);
		stage5Button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				stage5Button.setIcon(stage5);
				stage5Button.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				stage5Button.setIcon(stage5);
				stage5Button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				JOptionPane.showMessageDialog(board, "Highscore: \n" + board.sm.getHighscoreString());
				
				
				background.setVisible(false);
				scrollPane.setVisible(false);
				add(board);

				setContentPane(board);
				board.timer.start();
				
			}
		});

		scrollPane = new JScrollPane(background);
		setContentPane(scrollPane);

		background.setLayout(null);

	}

	JPanel background = new JPanel() {
		
		private static final long serialVersionUID = 1L;

		public void paint(Graphics g) {
			g.drawImage(introBackground.getImage(), 0, 0, null);
			setOpaque(false);
			paintComponents(g);
		}
	};

	public void paint(Graphics g) {
		screenImage = createImage(board.getBoardWidth() + OFFSET, board.getBoardHeight() + 2 * OFFSET);
		screenGraphic = screenImage.getGraphics();
		screenDraw(screenGraphic);
		g.drawImage(screenImage, 0, 0, null);
	}

	public void screenDraw(Graphics g) {
		g.drawImage(introBackground.getImage(), 0, 0, null);
		paintComponents(g);
		this.repaint();
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			Sokoban game = new Sokoban();
			game.setVisible(true);
			Sound sound = new Sound("src/resources/game.wav", -1);
			sound.play();
		});
	}
}
