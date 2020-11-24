package com.zetcode;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Sokoban extends JFrame implements Serializable {// main 클래스

	private static final long serialVersionUID = 1L;

	private Image screenImage;
	private Graphics screenGraphic;

	private ImageIcon introBackground = new ImageIcon("src/resources/firstbackground.jpg");

	private ImageIcon startButtonBasicImage = new ImageIcon("src/resources/start1.png");
	private ImageIcon startButtonEnteredImage = new ImageIcon("src/resources/start2.png");
	private ImageIcon quitButtonBasicImage = new ImageIcon("src/resources/quit1.png");
	private ImageIcon quitButtonEnteredImage = new ImageIcon("src/resources/quit2.png");

	private ImageIcon continueButtonBasicImage = new ImageIcon("src/resoures/continue1.png");
	private ImageIcon continueButtonEnteredImage = new ImageIcon("src/resoures/continue2.png");

	private ImageIcon loadButtonBasicImage = new ImageIcon("src/resources/load1.png");
	private ImageIcon loadButtonEnteredImage = new ImageIcon("src/resources/load2.png");

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

	private ImageIcon saveGame = new ImageIcon("src/resources/save1.png");
	private ImageIcon saveGameEntered = new ImageIcon("src/resources/save2.png");

	private JButton quitButton = new JButton(quitButtonBasicImage);
	private JButton startButton = new JButton(startButtonBasicImage);
	private JButton continueButton = new JButton(continueButtonBasicImage);
	private JButton loadButton = new JButton(loadButtonBasicImage);

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
	private JButton saveGameButton = new JButton(saveGame);

	private int second;

	private boolean wasStarted = false;

	private Board board = new Board();
//	private SavedState state;
	
	public Sokoban() {

		initUI();
	}

	public void saveGame(int level) {
		try {
//			state = new SavedState(board);
			FileOutputStream fos = new FileOutputStream("gamefile" + level + ".sav");
			BufferedOutputStream buffer = new BufferedOutputStream(fos);
			ObjectOutputStream oos = new ObjectOutputStream(buffer);
//			oos.writeObject(state);
			oos.writeObject(board);
			oos.flush();
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadGame(int level) {
		try {
			if(saveFileExists(level)) {
				FileInputStream fis = new FileInputStream("gamefile" + level + ".sav");
				BufferedInputStream buffer = new BufferedInputStream(fis);
				ObjectInputStream ois = new ObjectInputStream(buffer);
//				state = (SavedState) ois.readObject();
//				this.board = state.board;
				board = (Board) ois.readObject();
				ois.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public boolean saveFileExists(int level) {
		File f = new File("gamefile" + level + ".sav");
		return f.exists();
	}

	private void basicButtonMethod(JButton name, int x, int y, int width, int height) {
		name.setBounds(x, y, width, height);
		name.setBorderPainted(false);
		name.setContentAreaFilled(false);
		name.setFocusPainted(false);
	}

	private void buttonForEachStage(JButton name, int stagenum, ImageIcon img1, ImageIcon img2) {
		name.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				name.setIcon(img1);
				name.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				name.setIcon(img2);
				name.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				Board.currentLevel = stagenum - 1;
				JOptionPane.showMessageDialog(board, "Highscore: \n" + Board.sm.getHighscoreString());

				setContentPane(board);
				board.restartLevel();
				board.timer.start();
				background.setVisible(false);
			}
		});
	}

	private void selectStageButtonMethod(JButton name) {
		name.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				name.setIcon(stageSelectEntered);
				name.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				name.setIcon(stageSelect);
				name.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				background.setVisible(true);
				setContentPane(background);
			}
		});
	}

	private void restartButtonMethod(JButton name) {
		name.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				name.setIcon(restartEntered);
				name.setCursor(new Cursor(Cursor.HAND_CURSOR));
				// Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3", false);
				// buttonEnteredMusic.start();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				name.setIcon(restart);
				name.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
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
	}

	private void initUI() {

		setTitle("Sokoban");

		setSize(1280, 720);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);// 프레임을 화면 가운데에 배치

		// 종료버튼
		basicButtonMethod(quitButton, 490, 550, 250, 60);
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
		basicButtonMethod(startButton, 430, 350, 380, 100);
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
				loadButton.setVisible(false);
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

		// 불러오기 버튼
		basicButtonMethod(loadButton, 430, 450, 380, 100);
		loadButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				loadButton.setIcon(loadButtonEnteredImage);
				loadButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				loadButton.setIcon(loadButtonBasicImage);
				loadButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				startButton.setVisible(false);
				quitButton.setVisible(false);
				loadButton.setVisible(false);
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

				setContentPane(board);
				loadGame(Board.getCurrentLevel());
				board.requestFocusInWindow();
			}
		});
		background.add(loadButton);

		// 계속하기 버튼
		continueButton.setBounds(430, 500, 380, 100);
		continueButton.setBorderPainted(false);
		continueButton.setContentAreaFilled(false);
		continueButton.setFocusPainted(false);

		background.add(continueButton);

		// backToMenu 버튼
		basicButtonMethod(backToMenuButton, 30, 20, 310, 100);
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
				if (wasStarted) {
					continueButton.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseEntered(MouseEvent e) {
							continueButton.setIcon(continueButtonEnteredImage);
							continueButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
						}

						@Override
						public void mouseExited(MouseEvent e) {
							continueButton.setIcon(continueButtonBasicImage);
							continueButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
						}

						@Override
						public void mousePressed(MouseEvent e) {
							startButton.setVisible(false);
							quitButton.setVisible(false);
							continueButton.setVisible(false);
							loadButton.setVisible(false);
							introBackground = new ImageIcon("src/resources/nightcity.jpg");
							background.add(backToMenuButton);
							backToMenuButton.setVisible(true);
							// loadGame();
							setContentPane(board);
							board.timer.start();

							board.requestFocusInWindow();
						}
					});
				}
				startButton.setVisible(true);
				quitButton.setVisible(true);
				continueButton.setVisible(true);
				loadButton.setVisible(true);
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
		basicButtonMethod(stage1Button, 100, 300, 125, 44);
		buttonForEachStage(stage1Button, 1, stage1Entered, stage1Basic);

		// 스테이지2 버튼
		basicButtonMethod(stage2Button, 320, 300, 125, 44);
		buttonForEachStage(stage2Button, 2, stage2Entered, stage2Basic);

		// 스테이지3 버튼
		basicButtonMethod(stage3Button, 540, 300, 125, 44);
		buttonForEachStage(stage3Button, 3, stage3Entered, stage3Basic);

		// 스테이지4 버튼
		basicButtonMethod(stage4Button, 760, 300, 125, 44);
		buttonForEachStage(stage4Button, 4, stage4Entered, stage4Basic);

		// 스테이지5 버튼
		basicButtonMethod(stage5Button, 980, 300, 125, 44);
		buttonForEachStage(stage5Button, 5, stage5Entered, stage5Basic);

		// 스테이지 선택 버튼(게임오버)
		basicButtonMethod(stageSelectButton, 450, 430, 350, 50);
		selectStageButtonMethod(stageSelectButton);

		GameOver.add(stageSelectButton);

		// 재시작 버튼(게임오버)
		basicButtonMethod(restartButton, 440, 500, 350, 50);
		restartButtonMethod(restartButton);

		GameOver.add(restartButton);

		// 스테이지 선택 버튼(게임클리어)
		basicButtonMethod(stageSelectButton2, 450, 430, 350, 50);
		selectStageButtonMethod(stageSelectButton2);

		GameClear.add(stageSelectButton2);

		// 재시작 버튼(게임클리어)
		basicButtonMethod(restartButton2, 440, 500, 350, 50);
		restartButtonMethod(restartButton2);

		GameClear.add(restartButton2);

		// 스테이지 선택 버튼(게임중)
		basicButtonMethod(inGameStageSelectButton, 30, 30, 350, 50);
		selectStageButtonMethod(inGameStageSelectButton);

		board.add(inGameStageSelectButton);

		// 게임 저장 버튼(게임중)
		saveGameButton.setBounds(30, 100, 350, 50);
		saveGameButton.setBorderPainted(false);
		saveGameButton.setContentAreaFilled(false);
		saveGameButton.setFocusPainted(false);
		saveGameButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				saveGameButton.setIcon(saveGameEntered);
				saveGameButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				saveGameButton.setIcon(saveGame);
				saveGameButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				saveGame(Board.getCurrentLevel());
				JOptionPane.showMessageDialog(board, "Game Saved");
			}
		});
		board.add(saveGameButton);

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
			Sound sound = new Sound("src/resources/game.wav", -1);
			Sokoban game = new Sokoban();
			game.setVisible(true);
			sound.play();
		});
	}
}