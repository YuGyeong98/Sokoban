package com.zetcode;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Sokoban extends JFrame {// main 클래스

	private static final long serialVersionUID = 1L;

	private Image screenImage;
	private Graphics screenGraphic;

	private ImageIcon introBackground = new ImageIcon("src/resources/firstbackground.jpg");

	private ImageIcon startButtonBasicImage = new ImageIcon("src/resources/start1.png");
	private ImageIcon startButtonEnteredImage = new ImageIcon("src/resources/start2.png");
	private ImageIcon quitButtonBasicImage = new ImageIcon("src/resources/quit1.png");
	private ImageIcon quitButtonEnteredImage = new ImageIcon("src/resources/quit2.png");

	private ImageIcon backToMenuBasicImage = new ImageIcon("src/resources/backtomenu1.png");
	private ImageIcon backToMenuEnteredImage = new ImageIcon("src/resources/backtomenu2.png");

	private ImageIcon stage1Basic = new ImageIcon("src/resources/stage1basic.png");
	private ImageIcon stage1Entered = new ImageIcon("src/resources/stage1entered.png");
	private ImageIcon stage2Basic = new ImageIcon("src/resources/stage2basic.png");
	private ImageIcon stage2Entered = new ImageIcon("src/resources/stage2entered.png");
	private ImageIcon stage3Basic = new ImageIcon("src/resources/stage3basic.png");
	private ImageIcon stage3Entered = new ImageIcon("src/resources/stage3entered.png");
	private ImageIcon stage4Basic = new ImageIcon("src/resources/stage4basic.png");
	private ImageIcon stage4Entered = new ImageIcon("src/resources/stage4entered.png");
	private ImageIcon stage5Basic = new ImageIcon("src/resources/stage5basic.png");
	private ImageIcon stage5Entered = new ImageIcon("src/resources/stage5entered.png");

	private ImageIcon stageSelect = new ImageIcon("src/resources/stageselect.png");
	private ImageIcon restart = new ImageIcon("src/resources/restart.png");
	private ImageIcon stageSelectEntered = new ImageIcon("src/resources/stageselect2.png");
	private ImageIcon restartEntered = new ImageIcon("src/resources/restart2.png");

	private JButton quitButton = new JButton(quitButtonBasicImage);
	private JButton startButton = new JButton(startButtonBasicImage);

	private JButton stage1Button = new JButton(stage1Basic);
	private JButton stage2Button = new JButton(stage2Basic);
	private JButton stage3Button = new JButton(stage3Basic);
	private JButton stage4Button = new JButton(stage4Basic);
	private JButton stage5Button = new JButton(stage5Basic);

	private JButton stageSelectButton = new JButton(stageSelect);
	private JButton restartButton = new JButton(restart);
	private JButton stageSelectButton2 = new JButton(stageSelect);
	private JButton restartButton2 = new JButton(restart);

	private JButton inGameStageSelectButton = new JButton(stageSelect);
	private JButton backToMenuButton = new JButton(backToMenuBasicImage);

	private int second;

	Board board = new Board();

	public Sokoban() {

		initUI();
	}

	private void initUI() {

		setTitle("Sokoban");

		setSize(1280, 720);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);// 프레임을 화면 가운데에 배치

		// 종료버튼
		quitButton.setBounds(490, 450, 250, 60);
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
		startButton.setBounds(430, 350, 380, 100);
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
				introBackground = new ImageIcon("src/resources/nightcity.jpg");
				background.add(backToMenuButton);
				background.add(stage1Button);
				background.add(stage2Button);
				background.add(stage3Button);
				background.add(stage4Button);
				background.add(stage5Button);

				stage1Button.setVisible(true);
				stage2Button.setVisible(true);
				stage3Button.setVisible(true);
				stage4Button.setVisible(true);
				stage5Button.setVisible(true);
				backToMenuButton.setVisible(true);
			}
		});

		background.add(startButton);

		// backToMenu 버튼
		backToMenuButton.setBounds(30, 20, 310, 100);
		backToMenuButton.setBorderPainted(false);
		backToMenuButton.setContentAreaFilled(false);
		backToMenuButton.setFocusPainted(false);
		backToMenuButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				backToMenuButton.setIcon(backToMenuEnteredImage);
				backToMenuButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				backToMenuButton.setIcon(backToMenuBasicImage);
				backToMenuButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				startButton.setVisible(true);
				quitButton.setVisible(true);
				stage1Button.setVisible(false);
				stage2Button.setVisible(false);
				stage3Button.setVisible(false);
				stage4Button.setVisible(false);
				stage5Button.setVisible(false);
				backToMenuButton.setVisible(false);
				introBackground = new ImageIcon("src/resources/firstbackground.jpg");
			}
		});

		// 스테이지1 버튼
		stage1Button.setBounds(100, 300, 125, 44);
		stage1Button.setBorderPainted(false);
		stage1Button.setContentAreaFilled(false);
		stage1Button.setFocusPainted(false);
		stage1Button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				stage1Button.setIcon(stage1Entered);
				stage1Button.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				stage1Button.setIcon(stage1Basic);
				stage1Button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				Board.currentLevel = 0;
				JOptionPane.showMessageDialog(board, "Highscore: \n" + Board.sm.getHighscoreString());

				setContentPane(board);
				board.restartLevel();
				board.timer.start();

				background.setVisible(false);
				stage1Button.setVisible(false);
				stage2Button.setVisible(false);
				stage3Button.setVisible(false);
				stage4Button.setVisible(false);
				stage5Button.setVisible(false);
			}
		});

		// 스테이지2 버튼
		stage2Button.setBounds(320, 300, 125, 44);
		stage2Button.setBorderPainted(false);
		stage2Button.setContentAreaFilled(false);
		stage2Button.setFocusPainted(false);
		stage2Button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				stage2Button.setIcon(stage2Entered);
				stage2Button.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				stage2Button.setIcon(stage2Basic);
				stage2Button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				Board.currentLevel = 1;
				JOptionPane.showMessageDialog(board, "Highscore: \n" + Board.sm.getHighscoreString());

				setContentPane(board);
				board.restartLevel();
				board.timer.start();

				background.setVisible(false);
				stage1Button.setVisible(false);
				stage2Button.setVisible(false);
				stage3Button.setVisible(false);
				stage4Button.setVisible(false);
				stage5Button.setVisible(false);
			}
		});

		// 스테이지3 버튼
		stage3Button.setBounds(540, 300, 125, 44);
		stage3Button.setBorderPainted(false);
		stage3Button.setContentAreaFilled(false);
		stage3Button.setFocusPainted(false);
		stage3Button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				stage3Button.setIcon(stage3Entered);
				stage3Button.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				stage3Button.setIcon(stage3Basic);
				stage3Button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				Board.currentLevel = 2;
				JOptionPane.showMessageDialog(board, "Highscore: \n" + Board.sm.getHighscoreString());

				setContentPane(board);
				board.restartLevel();
				board.timer.start();

				background.setVisible(false);
				stage1Button.setVisible(false);
				stage2Button.setVisible(false);
				stage3Button.setVisible(false);
				stage4Button.setVisible(false);
				stage5Button.setVisible(false);
			}
		});

		// 스테이지4 버튼
		stage4Button.setBounds(760, 300, 125, 44);
		stage4Button.setBorderPainted(false);
		stage4Button.setContentAreaFilled(false);
		stage4Button.setFocusPainted(false);
		stage4Button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				stage4Button.setIcon(stage4Entered);
				stage4Button.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				stage4Button.setIcon(stage4Basic);
				stage4Button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				Board.currentLevel = 3;
				JOptionPane.showMessageDialog(board, "Highscore: \n" + Board.sm.getHighscoreString());

				setContentPane(board);
				board.restartLevel();
				board.timer.start();

				background.setVisible(false);
				stage1Button.setVisible(false);
				stage2Button.setVisible(false);
				stage3Button.setVisible(false);
				stage4Button.setVisible(false);
				stage5Button.setVisible(false);
			}
		});

		// 스테이지5 버튼
		stage5Button.setBounds(980, 300, 125, 44);
		stage5Button.setBorderPainted(false);
		stage5Button.setContentAreaFilled(false);
		stage5Button.setFocusPainted(false);
		stage5Button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				stage5Button.setIcon(stage5Entered);
				stage5Button.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				stage5Button.setIcon(stage5Basic);
				stage5Button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				Board.currentLevel = 4;
				JOptionPane.showMessageDialog(board, "Highscore: \n" + Board.sm.getHighscoreString());

				setContentPane(board);

				board.restartLevel();
				board.timer.start();

				background.setVisible(false);
				stage1Button.setVisible(false);
				stage2Button.setVisible(false);
				stage3Button.setVisible(false);
				stage4Button.setVisible(false);
				stage5Button.setVisible(false);
			}
		});

		// 스테이지 선택 버튼(게임오버)
		stageSelectButton.setBounds(450, 430, 350, 50);
		stageSelectButton.setBorderPainted(false);
		stageSelectButton.setContentAreaFilled(false);
		stageSelectButton.setFocusPainted(false);
		stageSelectButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				stageSelectButton.setIcon(stageSelectEntered);
				stageSelectButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				stageSelectButton.setIcon(stageSelect);
				stageSelectButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				background.setVisible(true);

				setContentPane(background);

				stage1Button.setVisible(true);
				stage2Button.setVisible(true);
				stage3Button.setVisible(true);
				stage4Button.setVisible(true);
				stage5Button.setVisible(true);

			}
		});

		GameOver.add(stageSelectButton);

		// 재시작 버튼(게임오버)
		restartButton.setBounds(440, 500, 350, 50);
		restartButton.setBorderPainted(false);
		restartButton.setContentAreaFilled(false);
		restartButton.setFocusPainted(false);
		restartButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				restartButton.setIcon(restartEntered);
				restartButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				restartButton.setIcon(restart);
				restartButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {

				board.restartLevel();
				board.timer.start();
				setContentPane(board);
				board.setFocusable(true);
				board.requestFocusInWindow();

			}
		});

		GameOver.add(restartButton);

		// 스테이지 선택 버튼(게임클리어)
		stageSelectButton2.setBounds(450, 430, 350, 50);
		stageSelectButton2.setBorderPainted(false);
		stageSelectButton2.setContentAreaFilled(false);
		stageSelectButton2.setFocusPainted(false);
		stageSelectButton2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				stageSelectButton2.setIcon(stageSelectEntered);
				stageSelectButton2.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				stageSelectButton2.setIcon(stageSelect);
				stageSelectButton2.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				background.setVisible(true);

				setContentPane(background);

				stage1Button.setVisible(true);
				stage2Button.setVisible(true);
				stage3Button.setVisible(true);
				stage4Button.setVisible(true);
				stage5Button.setVisible(true);
			}
		});

		GameClear.add(stageSelectButton2);

		// 재시작 버튼(게임클리어)
		restartButton2.setBounds(440, 500, 350, 50);
		restartButton2.setBorderPainted(false);
		restartButton2.setContentAreaFilled(false);
		restartButton2.setFocusPainted(false);
		restartButton2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				restartButton2.setIcon(restartEntered);
				restartButton2.setCursor(new Cursor(Cursor.HAND_CURSOR));
				// Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3", false);
				// buttonEnteredMusic.start();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				restartButton2.setIcon(restart);
				restartButton2.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				setContentPane(board);

				board.restartLevel();
				board.timer.start();
				board.setFocusable(true);
				board.requestFocusInWindow();

			}
		});

		GameClear.add(restartButton2);

		// 스테이지 선택 버튼(게임중)
		inGameStageSelectButton.setBounds(30, 30, 350, 50);
		inGameStageSelectButton.setBorderPainted(false);
		inGameStageSelectButton.setContentAreaFilled(false);
		inGameStageSelectButton.setFocusPainted(false);
		inGameStageSelectButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				inGameStageSelectButton.setIcon(stageSelectEntered);
				inGameStageSelectButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				inGameStageSelectButton.setIcon(stageSelect);
				inGameStageSelectButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				background.setVisible(true);

				setContentPane(background);

				stage1Button.setVisible(true);
				stage2Button.setVisible(true);
				stage3Button.setVisible(true);
				stage4Button.setVisible(true);
				stage5Button.setVisible(true);
			}
		});

		board.add(inGameStageSelectButton);

		setContentPane(background);

		background.setLayout(null);
		GameOver.setLayout(null);
		GameClear.setLayout(null);
		board.setLayout(null);
	}

	JPanel GameOver = new JPanel() {

		private static final long serialVersionUID = 1L;

		public void paint(Graphics g) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 1280, 720);
			g.setColor(Color.YELLOW);
			g.setFont(new Font("DOSMyungjo", Font.BOLD, 100));
			g.drawString("Game Over", 380, 300);
			setOpaque(false);
			paintComponents(g);
		}
	};

	JPanel GameClear = new JPanel() {

		private static final long serialVersionUID = 1L;

		public void paint(Graphics g) {
			board.finalScore = board.currentSteps - (board.coinScore * 5);
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 1280, 720);
			g.setColor(Color.YELLOW);
			g.setFont(new Font("DOSMyungjo", Font.PLAIN, 60));
			g.drawString("Game Clear", 480, 120);
			g.setFont(new Font("DOSMyungjo", Font.PLAIN, 25));
			g.drawString("Steps ", 540, 190);
			g.drawString(String.valueOf(board.currentSteps), 640, 190);
			g.drawString("Coin ", 540, 230);
			g.drawString(String.valueOf(board.coinScore), 640, 230);
			g.drawString("Time ", 540, 270);
			if (board.minute < 10 || second < 10) {
				g.drawString(String.valueOf(board.ddMinute) + ":" + String.valueOf(board.ddSecond), 640, 270);
			} else {
				g.drawString(String.valueOf(board.ddMinute) + ":" + String.valueOf(board.ddSecond), 640, 270);
			}
			g.setColor(Color.CYAN);
			g.drawString("Final Score = Steps - coin X 5 ", 440, 340);
			g.drawString("Final Score ", 520, 400);
			g.drawString(String.valueOf(board.finalScore), 700, 400);
			g.setFont(new Font("DOSMyungjo", Font.PLAIN, 20));
			setOpaque(false);
			paintComponents(g);
		}
	};

	JPanel background = new JPanel() {

		private static final long serialVersionUID = 1L;

		public void paint(Graphics g) {
			g.drawImage(introBackground.getImage(), 0, 0, null);
			setOpaque(false);
			paintComponents(g);
		}
	};

	public void paint(Graphics g) {
		screenImage = createImage(1280, 720);
		screenGraphic = screenImage.getGraphics();
		screenDraw(screenGraphic);
		g.drawImage(screenImage, 0, 0, null);
		if (board.getIsSokoDead()) {
			setContentPane(GameOver);
			board.setIsSokoDead(false);
		}
		if (board.getIsCompleted()) {
			board.setIsCompleted(false);
			setContentPane(GameClear);
		}
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