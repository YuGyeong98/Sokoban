package com.zetcode;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JPanel;
//게임에 저장이 되어야 할 데이터들을 여기에 저장한다.
public class Board2 extends JPanel{
	
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
    
    public int score = 0;
    public int currentSteps;
    private Header header;
    private Score currentScore;
    
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
    
    private String level =
    		//2
//    		 " #####   #####\n"
//    		+"#    #####   #\n"
//    		+"#^$.$  @  .$. #\n"
//    		+"# . .#####$   #\n"
//    		+"# $.$     .$. #\n"
//    		+"#    #####   !#\n"
//    		+" #####   #####\n";
    		//3
//    		"       #####\n"
//    		+"    ####   #\n"
//    		+"#####^  $# #\n"
//    		+"#   # #.^ ####\n"
//    		+"#   $ $....  #\n"
//    		+"#######$#$##!#\n"
//    		+"      #  @   #\n"
//    		+"      ########\n";  
    		//4
//    		"##########\n"
//    		+"#  #  #  #\n"
//    		+"#.......@#\n"
//    		+"# $##.#$ #\n"
//    		+"#  $     #\n"
//    		+"#  ##$#$##\n"
//    		+"# ^#  #  #\n"
//    		+"# $$  $ ^#\n"
//    		+"#  #  #  #\n"
//    		+"#  #  #  #\n"
//    		+"#! #  #  #\n"
//    		+"##########\n";
    		//5
    		"  #####\n"
    		+"###   ###\n"
    		+"#   $  ^#\n"
    		+"# $$$$$ #\n"
    		+"#   $   #\n"
    		+"### $# ##     #######\n"
    		+"  #  # #      #. . ^#\n"
    		+"  # $# #      #. . ^#\n"
    		+"  #  # #      #. . ^#\n"
    		+"  #$## ########. . ^#\n"
    		+"  # #          . . ^#\n"
    		+"  # #    ######.##@##\n"
    		+"  # #### #    #.    #\n"
    		+"  # $ $  #    #######\n"
    		+"  #!    ##\n"
    		+"  #######\n";
    
    public Board2() {

        initBoard();
    }

    private void initBoard() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        initWorld();
    }

    public int getBoardWidth() {
        return this.w;
    }

    public int getBoardHeight() {
        return this.h;
    }
 
    private void initWorld() {
    	//게임을 진행하면서  walls,baggs,areas컨테이너를 채운다.
    	
        //컨테이너
        walls = new ArrayList<>();//new에서 타입 생략 가능/ Wall객체들만 사용 가능
        baggs = new ArrayList<>();
        areas = new ArrayList<>();
        coins = new ArrayList<>();
        
        int x = OFFSET;
        int y = OFFSET;

        Wall wall;
        Baggage b;
        Area a;
        Coin c;
        
        for (int i = 0; i < level.length(); i++) {
        	
            char item = level.charAt(i);//문자열에서 인자로 주어진 값에 해당하는 문자를 리턴한다.

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
                    baggs.add(b);//baggs컨테이너에 b추가
                    x += SPACE;//x위치에 20만큼 더한다.
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
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        
        header = new Header();
        header.UpdateSteps(getCurrentSteps());
        header.render(g);
        
        leftbaggs(g);
        coinCheck();
       
        currentScore = new Score();
        currentScore.UpdateScore(score);
        currentScore.render(g);
        
	    ArrayList<Actor> world = new ArrayList<>();
	    
	    world.addAll(walls);
	    world.addAll(areas);
	    world.addAll(baggs);
	    world.addAll(coins);
	    world.add(soko);
	    world.add(enemy);
	    
	    for (int i = 0; i < world.size(); i++) {
	
	        Actor item = world.get(i);//컨테이너들을 반복해서 빼낸다.
	
	        if (item instanceof Area) {
	            g.drawImage(item.getImage(), item.x() + 8, item.y() + 8, this);
	        } 
	        else if(item instanceof Player || item instanceof Enemy) {
	        	g.drawImage(item.getImage(), item.x() + 7, item.y() + 1, this);
	        }
	        else{
	            g.drawImage(item.getImage(), item.x(), item.y(), this);
	        }
	
	        if (isCompleted) {
	            g.setColor(Color.WHITE);
	            g.drawString("Completed", 25, 20);
	        }
	        
	        if (isSokoDead) {
            	gameOver(g);
            }
	    }
	    
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //컴포넌트가 그리져야 하는 시점마다, 크기가 변경되거나 위치가 변경되거나 컴포넌트가 가려졌던 것이 사라지는 등
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

            	 switch (key) {//어떤 키가 눌러졌는지 확인한다. 우리는 cursor키를 통해 소코반물체를 제어한다.
             	//왼쪽을 눌렀다면, 소코반이 wall이나 baggage에 충돌했는지 체크한다. 충돌하지 않았다면, 소코반을 왼쪽으로 옮긴다.
                 case KeyEvent.VK_LEFT:
                 	
                     if (checkWallCollision(soko, LEFT_COLLISION)) {
                         return;
                     }
                     
                     if (checkBagCollision(LEFT_COLLISION)) {
                         return;
                     }
                     
                     soko.move(-SPACE, 0);
                     addStep();
                     tick();
                     isSokoDead();
                     
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
                     tick();
                     isSokoDead();
                     
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
                     tick();
                     isSokoDead();
                     
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
                     tick();
                     isSokoDead();
                     
                     break;
                     
                 case KeyEvent.VK_R://R키를 누르면 레벨을 재시작한다.
                     restartLevel();
                     resetSteps();
                     score = 0;
                     currentScore.save(score);
                     break;
                     
                 default:
                     break;
             }
            	 
             repaint();
      }
           
    }
    
    
    private boolean checkWallCollision(Actor actor, int type) {
    	//소코반이나 baggage가 벽을 통과하지 않도록 만들어졌다.
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
    	//baggage는 벽, 소코반  물체 또는 다른 baggage와 충돌할 수 있다. 다른 물체와 충돌하지 않아야 baggage 옮기기 가능
    	//baggage 옮길때는 isCompleted()에 의해 레벨이 끝났는지 확인할 때다.
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

        if (finishedBags == nOfBags) {//finishedBags와 nOfBags(게임 내 baggage수)와 같을 때 게임은 종료된다.
            
            isCompleted = true;
            repaint();
        }
    }
    
    private void restartLevel() {//재시작

        areas.clear();
        baggs.clear();
        walls.clear();

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
    public void setCurrentSteps(int i) {
    	this.currentSteps = i;
    }
    public void resetSteps() {
    	this.currentSteps = 0;
    }
    
    public void coinCheck() {
    	for(int i=0;i<coins.size();i++) {
    		Sound sound = new Sound("src/resources/coin.wav",0);
	    	Coin coin = coins.get(i);
	    	if(soko.getRect().intersects(coin.getRect())){
	    		score += 10;
	    		currentScore.save(score);
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
		g.drawString("Leftboxes: ", 500, 70);
		g.drawString(Integer.toString(leftover), 640, 70);
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
    while(true) {
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
        	 	
        	if(enemy.y() < soko.y()) {
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

    	    			}
    	    		}
    	    		else {
    	    			if(canEnemyMove(enemy.x(),enemy.y() - spd)) {
    	    				enemy.move(0, -spd);
    	    				System.out.println("find up(right)");
    	    				state = smart;
    	    			}
    	    		}
    	    		
    	    		if(canEnemyMove(enemy.x() + spd, enemy.y())) {
    	    			enemy.move(spd, 0);
    					System.out.println("find right(right)");
    					state = smart;
    	    		}
    	          }
    	    	
    	    	else if(enemy.lastDir == left) {
    	    		if(enemy.y() < soko.y()) {
    	    			if(canEnemyMove(enemy.x(), enemy.y() + spd)) {
    	    				enemy.move(0, spd);
    	    				System.out.println("find down(left)");
    	    				state = smart;
    	    			}
    	    		}
    	    		else {
    	    			if(canEnemyMove(enemy.x(),enemy.y() - spd)) {
    	    				enemy.move(0, -spd);
    	    				System.out.println("find up(left)");
    	    				state = smart;
    	    			}
    	    		}
    	    		
    	    		if(canEnemyMove(enemy.x() - spd, enemy.y())) {
    	    			enemy.move(-spd, 0);
    					System.out.println("find left(left)");
    					state = smart;
    	    		}
    	          }
    	    	
    	          else if(enemy.lastDir == up) {
    	        	  if(enemy.x() < soko.x()) {
    	      			if(canEnemyMove(enemy.x() + spd, enemy.y())) {
    	      				enemy.move(spd, 0);
    	    				System.out.println("find right(up)");
    	    				state = smart;
    	      			}
    	      		}
    	      		else {
    	      			if(canEnemyMove(enemy.x() - spd,enemy.y())) {
    	      				enemy.move(-spd, 0);
    	    				System.out.println("find left(up)");
    	    				state = smart;
    	      			}
    	      		}
    	      		
    	      		if(canEnemyMove(enemy.x(), enemy.y() - spd)) {
    	      			enemy.move(0, -spd);
    					System.out.println("find up(up)");
    					state = smart;
    	      		}
    	          }
    	    	
    	          else if(enemy.lastDir == down) {
    	        	  if(enemy.x() < soko.x()) {
    	      			if(canEnemyMove(enemy.x() + spd, enemy.y())) {
    	      				enemy.move(spd, 0);
    	    				System.out.println("find right(down)");
    	    				state = smart;
    	      			}
    	      		}
    	      		else {
    	      			if(canEnemyMove(enemy.x() - spd, enemy.y())) {
    	      				enemy.move(-spd, 0);
    	    				System.out.println("find left(down)");
    	    				state = smart;
    	      			}
    	      		}
    	      		
    	      		if(canEnemyMove(enemy.x(), enemy.y() + spd)) {
    	      			enemy.move(0, spd);
    					System.out.println("find down(down)");
    					state = smart;
    	      		}          
    	          }
    	    	}
    	}

    }

    }
    
    public void isSokoDead() {
    	if(enemy.x() == soko.x() && enemy.y() == soko.y()) {
    		isSokoDead = true;
    		repaint();
    	}
    }
    
    public void gameOver(Graphics g) {
    	g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
    	g.setColor(Color.YELLOW);
    	g.setFont(new Font("DOSMyungjo",Font.BOLD,40));
		g.drawString("Game Over", 255, 120);
		g.setFont(new Font("DOSMyungjo",Font.PLAIN,30));
		g.drawString("Score: ", 273, 200);
		g.drawString(String.valueOf(score),390,200);
		g.setFont(new Font("DOSMyungjo",Font.PLAIN,20));
		g.drawString(">> Press enter to restart game <<", 183, 300);
    }
    
}
