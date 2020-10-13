package com.zetcode;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	private final int OFFSET = 35;//윈도우 창의 테두리와 게임 사이의 가로 거리
    private final int SPACE = 32;//(벽 이미지 사이즈)
   
    private final int LEFT_COLLISION = 5;
    private final int RIGHT_COLLISION = 6;
    private final int TOP_COLLISION = 7;
    private final int BOTTOM_COLLISION = 8;
  
    private ArrayList<Wall> walls;
    private ArrayList<Baggage> baggs;
    private ArrayList<Area> areas;
    private ArrayList<Coin> coins;
    
    
    public static int currentLevel;
    public int coinScore = 0;
    public int currentSteps;
    
    public static ScoreManager sm = new ScoreManager();
    private Header header;
    public int finalScore = 0;
    
    private Player soko;
    private int w = 0;
    private int h = 0;
    
    private boolean isCompleted = false;
    
    private final int right = 0;
    private final int left = 1;
    private final int up = 2;
    private final int down = 3;
    
    private int spd = 32;
    
    private int smart = 1;
    private int find_path = 2;
    private int state = smart;
    
    private Enemy enemy;
    private boolean isSokoDead = false;
    
	JLabel counterLabel;
	
	Timer timer;
	int second;
	int minute;
	String ddSecond, ddMinute;
	DecimalFormat dFormat = new DecimalFormat("00");

	private String level[] = {
			//level1
            "    ######\n"
          + "    ##   #\n"
          + "    ##$ ^#\n"
          + "  ####  $##\n"
          + "  ##! $ $ #\n"
          + "#### # ## #   ######\n"
          + "##  ^# ## #####  ..#\n"
          + "## $  $          ..#\n"
          + "###### ### #@##  ..#\n"
          + "    ## ^   #########\n"
          + "    ########\n",
          
			//level2
            "        ########\n"
          + "        #     @#\n"
          + "        # $#  ##\n"
          + "        #     #\n"
          + "        ##  $ #\n"
          + "#########   #^###\n"
          + "#.     ##   $   #\n"
          + "##.             #\n"
          + "#.     ##########\n"
          + "########\n",
          
	        //level3
	   		"      ####\n"
	   	  + "####### @#\n"
	      + "#        #\n"
	   	  + "#   $##  #\n"
	   	  + "## # ..# #\n"
	   	  + " # $ ..^ #\n"
	   	  + " # #   # ##\n"
	   	  + " #   # #$ #\n"
	   	  + " #   $   ^#\n"
	   	  + " #  #######\n"
	   	  + " ####",
	   		
	   		//level4
	   		"  #################\n"
	   	  + "  #     # ^      .#\n"
	   	  + "  # ############ $#\n"
	   	  + "  # #      #      #\n"
	   	  + "  # # ###  #  #####\n"
	   	  + "  #^# #^   #     ^#\n"
	   	  + "  # # # #######  ##\n"
	   	  + "  # # #    ###   ##\n"
	   	  + "### # # ##     ####\n"
	   	  + "#!@   # ## ########\n"
	   	  + "#######  ^#########\n"
	   	  + "###################\n",

	   	    //level5
	   	    "        #######    \n"
	   	  + "    #####  #  #### \n"
	   	  + "    #   #        # \n"
	   	  + " #### #   ## ##  # \n"
	   	  + "##      # #  ## ###\n"
	   	  + "#  ### $#   $   $ #\n"
	   	  + "#.      # ##  #   #\n"
	   	  + "#.  #    @ # ### ##\n"
	   	  + "#.  #  ###        #\n"
	   	  + "######## ##   #   #\n"
	   	  + "          #########\n",
	   	  

	};

    
    public Board() {

        initBoard();
        
        counterLabel = new JLabel("");
        counterLabel.setLayout(null);
        counterLabel.setBounds(580, 30, 200, 100);
        counterLabel.setFont(new Font("나눔고딕", Font.PLAIN, 22));
        counterLabel.setForeground(Color.WHITE);
        setTimer();
        add(counterLabel);

        simpleTimer();
    }

    public void initBoard() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        initWorld();
    }

    public static int getCurrentLevel() {
    	return currentLevel;
    }
    
    public int getBoardWidth() {
        return this.w;
    }

    public int getBoardHeight() {
        return this.h;
    }
    
    public boolean getIsCompleted() {
    	return isCompleted;
    }
    
    public boolean getIsSokoDead() {
    	return isSokoDead;
    }
    
    public void setIsSokoDead(boolean isSokoDead) {
    	 this.isSokoDead = isSokoDead;
    }
    
    public void setIsCompleted(boolean isCompleted) {
    	this.isCompleted = isCompleted;
    }
    
    public int getMoves() {
    	return finalScore;
    }
    
    public void setTimer() {
        counterLabel.setText("Time  00:00");        
        second = 0;
        minute = 0;
    }
    
    public void simpleTimer() {
    	timer = new Timer(1000, new ActionListener() {
    		
    		@Override
    		public void actionPerformed(ActionEvent e) {
    			
    			second++;
    			
    			ddSecond = dFormat.format(second);
    			ddMinute = dFormat.format(minute);
    			
    			counterLabel.setText("Time  " + ddMinute + ":" + ddSecond);
    			
    			if(second == 60) {
    				second = 0;
    				minute++;
    				
        			ddSecond = dFormat.format(second);
        			ddMinute = dFormat.format(minute);
    				counterLabel.setText("Time  " + ddMinute + ":" + ddSecond);
    			}
    		}
    	});
    }
 
    private void initWorld() {

        walls = new ArrayList<>();
        baggs = new ArrayList<>();
        areas = new ArrayList<>();
        coins = new ArrayList<>();
        
        int x = OFFSET;
        int y = OFFSET;

        Wall wall;
        Baggage b;
        Area a;
        Coin c;
        
        for (int i = 0; i < level[currentLevel].length(); i++) {
        	
            char item = level[currentLevel].charAt(i);

            switch (item) {

                case '\n':
                    y += SPACE;

                    if (this.w < x) {
                        this.w = x;
                    }
                    x = OFFSET;
                    break;

                case '#':
                    wall = new Wall(x, y);
                    walls.add(wall);
                    x += SPACE;
                    break;

                case '$':
                    b = new Baggage(x, y);
                    baggs.add(b);
                    x += SPACE;
                    break;

                case '.':
                    a = new Area(x, y);
                    areas.add(a);
                    x += SPACE;
                    break;
                    
                case '^':
                	c = new Coin(x, y);
                	coins.add(c);
                	x += SPACE;
                	break;
                	
                case '@':
                    soko = new Player(x, y);
                    x += SPACE;
                    break;
                    
                case '!':
                	enemy = new Enemy(x, y);
                	x += SPACE;
                	break;

                case ' ':
                    x += SPACE;
                    break;

                default:
                    break;
            }
            h = y;
        }
        
    }   
    
    private void buildWorld(Graphics g) {
    	//게임을 윈도우에 그린다.

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 1280, 720);
        
        header = new Header();
        header.UpdateSteps(getCurrentSteps());
        header.UpdateLevels(getCurrentLevel());
        header.render(g);
        
        leftbaggs(g);
        coinCheck();        
        
	    ArrayList<Actor> world = new ArrayList<>();

	    world.addAll(walls);
	    world.addAll(areas);
	    world.addAll(baggs);
	    world.addAll(coins);
	    world.add(soko);
	    if(enemy != null) {
		    world.add(enemy);
	    }

	    
	    for (int i = 0; i < world.size(); i++) {
	
	        Actor item = world.get(i);
	
	        if (item instanceof Area) {
	            g.drawImage(item.getImage(), item.x() + 8 + 350, item.y() + 8 + 100, this);
	        } 
	        else if(item instanceof Player || item instanceof Enemy) {
	        	g.drawImage(item.getImage(), item.x() + 7 + 350, item.y() + 1 + 100, this);
	        }
	        else{
	            g.drawImage(item.getImage(), item.x() + 350, item.y() + 100, this);
	        }

	    }
	    
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        buildWorld(g);
    }
    
    private class TAdapter extends KeyAdapter {//키 입력받기

        @Override
        public void keyPressed(KeyEvent e) {

            if (isCompleted) {
                return;
            }
            
            if (isSokoDead) {
            	return;
            }

            int key = e.getKeyCode();

            	 switch (key) {

                 case KeyEvent.VK_LEFT:
                 	
                     if (checkWallCollision(soko, LEFT_COLLISION)) {
                         return;
                     }
                     
                     if (checkBagCollision(LEFT_COLLISION)) {
                         return;
                     }
                     
                     soko.move(-SPACE, 0);
                     addStep();
                 	if(enemy != null) {
                        tick();
                        isSokoDead();
                 	}
                     
                     break;
                     
                 case KeyEvent.VK_RIGHT:
                     
                     if (checkWallCollision(soko, RIGHT_COLLISION)) {
                         return;
                     }
                     
                     if (checkBagCollision(RIGHT_COLLISION)) {
                         return;
                     }
                     
                     soko.move(SPACE, 0);
                     addStep();
                  	if(enemy != null) {
                        tick();
                        isSokoDead();
                 	}
                     
                     break;
                     
                 case KeyEvent.VK_UP:
                     
                     if (checkWallCollision(soko, TOP_COLLISION)) {
                         return;
                     }
                     
                     if (checkBagCollision(TOP_COLLISION)) {
                         return;
                     }
                     
                     soko.move(0, -SPACE);
                     addStep();
                  	if(enemy != null) {
                        tick();
                        isSokoDead();
                 	}
     
                     break;
                     
                 case KeyEvent.VK_DOWN:
                     
                     if (checkWallCollision(soko, BOTTOM_COLLISION)) {
                         return;
                     }
                     
                     if (checkBagCollision(BOTTOM_COLLISION)) {
                         return;
                     }
                     
                     soko.move(0, SPACE);
                     addStep();
                  	if(enemy != null) {
                        tick();
                        isSokoDead();
                 	}
    
                     break;
                     
                 case KeyEvent.VK_R://R키를 누르면 레벨을 재시작한다.
                     restartLevel();                  
                     finalScore = 0;

                     break;
                     
                 default:
                     break;
             }
            	 
             repaint();
      }
           
    }
    
    
    private boolean checkWallCollision(Actor actor, int type) {
 
        switch (type) {
            
            case LEFT_COLLISION:
                
                for (int i = 0; i < walls.size(); i++) {
                    
                    Wall wall = walls.get(i);
                    
                    if (actor.isLeftCollision(wall)) {
                        
                        return true;
                    }
                }
                
                return false;
                
            case RIGHT_COLLISION:
                
                for (int i = 0; i < walls.size(); i++) {
                    
                    Wall wall = walls.get(i);
                    
                    if (actor.isRightCollision(wall)) {
                        return true;
                    }
                }
                
                return false;
                
            case TOP_COLLISION:
                
                for (int i = 0; i < walls.size(); i++) {
                    
                    Wall wall = walls.get(i);
                    
                    if (actor.isTopCollision(wall)) {
                        
                        return true;
                    }
                }
                
                return false;
                
            case BOTTOM_COLLISION:
                
                for (int i = 0; i < walls.size(); i++) {
                    
                    Wall wall = walls.get(i);
                    
                    if (actor.isBottomCollision(wall)) {
                        
                        return true;
                    }
                }
                
                return false;
                
            default:
                break;
        }
        return false;
    }

    private boolean checkBagCollision(int type) {

        switch (type) {
            
            case LEFT_COLLISION:
                
                for (int i = 0; i < baggs.size(); i++) {

                    Baggage bag = baggs.get(i);

                    if (soko.isLeftCollision(bag)) {

                        for (int j = 0; j < baggs.size(); j++) {
                            
                            Baggage item = baggs.get(j);
                            
                            if (!bag.equals(item)) {//baggage가 baggage와 충돌할 때
                                
                                if (bag.isLeftCollision(item)) {
                                    return true;
                                }
                            }
                            
                            if (checkWallCollision(bag, LEFT_COLLISION)) {//baggage가 벽과 충돌할 때
                                return true;
                            }
                        }
                        
                        bag.move(-SPACE, 0);
                        isCompleted();
                    }
                }
                
                return false;
                
            case RIGHT_COLLISION:
                
                for (int i = 0; i < baggs.size(); i++) {

                    Baggage bag = baggs.get(i);
                    
                    if (soko.isRightCollision(bag)) {
                        
                        for (int j = 0; j < baggs.size(); j++) {

                            Baggage item = baggs.get(j);
                            
                            if (!bag.equals(item)) {
                                
                                if (bag.isRightCollision(item)) {
                                    return true;
                                }
                            }
                            
                            if (checkWallCollision(bag, RIGHT_COLLISION)) {
                                return true;
                            }
                        }
                        
                        bag.move(SPACE, 0);
                        isCompleted();
                    }
                }
                return false;
                
            case TOP_COLLISION:
                
                for (int i = 0; i < baggs.size(); i++) {

                    Baggage bag = baggs.get(i);
                    
                    if (soko.isTopCollision(bag)) {
                        
                        for (int j = 0; j < baggs.size(); j++) {

                            Baggage item = baggs.get(j);

                            if (!bag.equals(item)) {
                                
                                if (bag.isTopCollision(item)) {
                                    return true;
                                }
                            }
                            
                            if (checkWallCollision(bag, TOP_COLLISION)) {
                                return true;
                            }
                        }
                        
                        bag.move(0, -SPACE);
                        isCompleted();
                    }
                }

                return false;
                
            case BOTTOM_COLLISION:
                
                for (int i = 0; i < baggs.size(); i++) {

                    Baggage bag = baggs.get(i);
                    
                    if (soko.isBottomCollision(bag)) {
                        
                        for (int j = 0; j < baggs.size(); j++) {

                            Baggage item = baggs.get(j);
                            
                            if (!bag.equals(item)) {
                                
                                if (bag.isBottomCollision(item)) {
                                    return true;
                                }
                            }
                            
                            if (checkWallCollision(bag,BOTTOM_COLLISION)) {
                                
                                return true;
                            }
                        }
                        
                        bag.move(0, SPACE);
                        isCompleted();
                    }
                }
                
                break;
                
            default:
                break;
        }

        return false;
    }

    public void isCompleted() {
    	//레벨이 끝났는지 확인한다.
    	//baggage의 수를 얻을 수 있다. 모든 baggage의 (x,y)좌표와 목적지를 비교한다.
        int nOfBags = baggs.size();
        
        int finishedBags = 0;
       
        for (int i = 0; i < nOfBags; i++) {
            
            Baggage bag = baggs.get(i);
            
            for (int j = 0; j < nOfBags; j++) {
                 
                Area area =  areas.get(j);
                
                if (bag.x() == area.x() && bag.y() == area.y()) {
                    
                    finishedBags += 1;
                }
            }
        }

        if (finishedBags == nOfBags) {//finishedBags와 nOfBags(게임 내 baggage수)와 같을 때 게임 클리어
            if(finalScore < 0) {
            	finalScore = 0;
            }
        	finalScore = currentSteps-(coinScore*5);
            isCompleted = true;
            timer.stop();
    		remove(counterLabel);
            sm.addScore(++finalScore);
            repaint();
        }
    }
    
    public void restartLevel() {//재시작

    	add(counterLabel);
    	setTimer();
        resetSteps();
        areas.clear();
        baggs.clear();
        walls.clear();
        enemy = null;
        coinScore = 0; 

        initWorld();
        state = smart;
        
        if (isCompleted) {
            isCompleted = false;
        }
        if (isSokoDead) {
        	isSokoDead = false;
        }
    }
    
    public int getCurrentSteps() {
    	return this.currentSteps;
    }
    public void addStep() {
    	this.currentSteps++;
    }
    public void resetSteps() {
    	this.currentSteps = 0;
    }
    
    public void coinCheck() {
    	for(int i=0;i<coins.size();i++) {
    		Sound sound = new Sound("src/resources/coin.wav",0);
	    	Coin coin = coins.get(i);
	    	if(soko.getRect().intersects(coin.getRect())){
	    		coinScore += 1;
	    		coins.remove(i);
	    		sound.play();
	    		break;
	    	}
	    }
    }
    
    public void leftbaggs(Graphics g) {
    	int nOfBags = baggs.size();
    	int leftover = baggs.size();
    	
    	for (int i = 0; i < nOfBags; i++) {
            
            Baggage bag = baggs.get(i);
            
            for (int j = 0; j < nOfBags; j++) {
                 
                Area area =  areas.get(j);
                
                if (bag.x() == area.x() && bag.y() == area.y()) {
            		leftover -= 1;
                }
            }
        }
    	g.setFont(new Font("나눔고딕",Font.PLAIN,22));
		g.setColor(Color.WHITE);
		g.drawString("Leftboxes: ", 900, 90);
		g.drawString(Integer.toString(leftover), 1040, 90);
    }
    
    public boolean canEnemyMove(int next_x, int next_y){
    	for (int i = 0; i < walls.size(); i++) {
    		Wall wall = walls.get(i);
    		if(wall.x() == next_x && wall.y() == next_y) {
    			return false;
    		}
    	}
    	
    	return true;
    }
    
    public void tick() {
    	 
    boolean move = false;
    while(enemy.x() != soko.x() || enemy.y() != soko.y()) {
    	if(state == smart) {
        	if(enemy.x() < soko.x()) {
        		if(canEnemyMove(enemy.x()+spd, enemy.y())) {
            		enemy.move(spd, 0);
            		move = true;
            		enemy.lastDir = right;
            		System.out.println("smart right");
            		break;
            	}
        	}
        	
        	else if(enemy.x() > soko.x()) {
        		if(canEnemyMove(enemy.x()-spd, enemy.y())) {
        			enemy.move(-spd, 0);
            		move = true;
        			enemy.lastDir = left;
        			System.out.println("smart left");
        			break;
        		}
        	}
        	 	
        	if(enemy.y() < soko.y() || enemy.y() == soko.y()) {
        		if(canEnemyMove(enemy.x(), enemy.y()+spd)) {
        			enemy.move(0, spd);
            		move = true;
        			enemy.lastDir = down;
        			System.out.println("smart down");
        			break;
        		}
        	}
        	
        	else if(enemy.y() > soko.y()) {
        		if(canEnemyMove(enemy.x(), enemy.y()-spd)) {
        			enemy.move(0, -spd);
            		move = true;
        			enemy.lastDir = up;
        			System.out.println("smart up");
        			break;
        		}
        	}

    }
    
    	
    	if(!move) {
    		state = find_path;
    		if(state == find_path) {
    	    	if(enemy.lastDir == right) {
    	    		
    	    		if(enemy.y() < soko.y()) {
    	    			if(canEnemyMove(enemy.x(), enemy.y() + spd)) {
    	    				enemy.move(0, spd);
    	    				System.out.println("find down(right)");
    	    				state = smart;
        					break;
    	    			}
    	    		}
    	    		else {
    	    			if(canEnemyMove(enemy.x(),enemy.y() - spd)) {
    	    				enemy.move(0, -spd);
    	    				System.out.println("find up(right)");
    	    				state = smart;
        					break;
    	    			}
    	    		}
    	    		
    	    		if(canEnemyMove(enemy.x() + spd, enemy.y())) {
    	    			enemy.move(spd, 0);
    					System.out.println("find right(right)");
    					state = smart;
    					break;
    	    		}
    	          }
    	    	
    	    	else if(enemy.lastDir == left) {
    	    		if(enemy.y() < soko.y()) {
    	    			if(canEnemyMove(enemy.x(), enemy.y() + spd)) {
    	    				enemy.move(0, spd);
    	    				System.out.println("find down(left)");
    	    				state = smart;
        					break;
    	    			}
    	    		}
    	    		else {
    	    			if(canEnemyMove(enemy.x(),enemy.y() - spd)) {
    	    				enemy.move(0, -spd);
    	    				System.out.println("find up(left)");
    	    				state = smart;
        					break;
    	    			}
    	    		}
    	    		
    	    		if(canEnemyMove(enemy.x() - spd, enemy.y())) {
    	    			enemy.move(-spd, 0);
    					System.out.println("find left(left)");
    					state = smart;
    					break;
    	    		}
    	          }
    	    	
    	          else if(enemy.lastDir == up) {
    	        	  if(enemy.x() < soko.x()) {
    	      			if(canEnemyMove(enemy.x() + spd, enemy.y())) {
    	      				enemy.move(spd, 0);
    	    				System.out.println("find right(up)");
    	    				state = smart;
        					break;
    	      			}
    	      		}
    	      		else {
    	      			if(canEnemyMove(enemy.x() - spd,enemy.y())) {
    	      				enemy.move(-spd, 0);
    	    				System.out.println("find left(up)");
    	    				state = smart;
        					break;
    	      			}
    	      		}
    	      		
    	      		if(canEnemyMove(enemy.x(), enemy.y() - spd)) {
    	      			enemy.move(0, -spd);
    					System.out.println("find up(up)");
    					state = smart;
    					break;
    	      		}
    	          }
    	    	
    	          else if(enemy.lastDir == down) {
    	        	  if(enemy.x() < soko.x()) {
    	      			if(canEnemyMove(enemy.x() + spd, enemy.y())) {
    	      				enemy.move(spd, 0);
    	    				System.out.println("find right(down)");
    	    				state = smart;
    	    				break;
    	      			}
    	      		}
    	      		else {
    	      			if(canEnemyMove(enemy.x() - spd, enemy.y())) {
    	      				enemy.move(-spd, 0);
    	    				System.out.println("find left(down)");
    	    				state = smart;
    	    				break;
    	      			}
    	      		}
    	      		
    	      		if(canEnemyMove(enemy.x(), enemy.y() + spd)) {
    	      			enemy.move(0, spd);
    					System.out.println("find down(down)");
    					state = smart;
    					break;
    	      		}          
    	          }
    	    	}
    		}

    	}

    }
    
    public void isSokoDead() {
    	if(enemy.x() == soko.x() && enemy.y() == soko.y()) {
    		isSokoDead = true;
    		remove(counterLabel);
    		repaint();
    	}
    }
    
}