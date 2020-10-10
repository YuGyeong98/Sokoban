package com.zetcode;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JPanel;
//���ӿ� ������ �Ǿ�� �� �����͵��� ���⿡ �����Ѵ�.
public class Board extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	private final int OFFSET = 35;//������ â�� �׵θ��� ���� ������ ���� �Ÿ�
    private final int SPACE = 32;//(�� �̹��� ������)
   
    private final int LEFT_COLLISION = 5;//���� �浹
    private final int RIGHT_COLLISION = 6;//������ �浹
    private final int TOP_COLLISION = 7;//��� �浹
    private final int BOTTOM_COLLISION = 8;//�ϴ� �浹 
  
//ArrayList - ũ�Ⱑ ������(�ڵ���)���� ���ϴ� ��������Ʈ/ ArrayList<Ÿ�Լ���>
    private ArrayList<Wall> walls;//walls�� ���� �� �ִ� �����̳�
    private ArrayList<Baggage> baggs;//baggs�� ���� �� �ִ� �����̳�
    private ArrayList<Area> areas;//areas�� ���� �� �ִ� �����̳�
    private ArrayList<Coin> coins;
    
    public int currentSteps;
    private Header header;
    private Timer timer;
    private Score currentScore;
    private int score = 0;
    
    private Player soko;
    private int w = 0;//width
    private int h = 0;//height
    
    private boolean isCompleted = false;
    
    private final int right = 0;
    private final int left = 1;
    private final int up = 2;
    private final int down = 3;
    
    private int spd = 20;
    
    private int smart = 1;
    private int find_path = 2;
    private int state = smart;
    
    private Enemy enemy;
    private boolean isSokoDead = false;

//#=��(wall), $=�̵��� ����(baggage), .=�츮�� �ڽ��� �Űܾ� �� ���(area), @=���ڹ�(sokoban)
    private String level =
              "    ######\n"
            + "    ##   #\n"
            + "    ##$ ^#\n"
            + "  ####  $##\n"
            + "  ##  $ $ #\n"
            + "#### # ## #   ######\n"
            + "##! ^# ## #####  ..#\n"
            + "## $  $          ..#\n"
            + "###### ### #@##  ..#\n"
            + "    ## ^   #########\n"
            + "    ########\n";
    
    public Board() {

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
    	//������ �����ϸ鼭  walls,baggs,areas�����̳ʸ� ä���.
    	
        //�����̳�
        walls = new ArrayList<>();//new���� Ÿ�� ���� ����/ Wall��ü�鸸 ��� ����
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
        	
            char item = level.charAt(i);//���ڿ����� ���ڷ� �־��� ���� �ش��ϴ� ���ڸ� �����Ѵ�.

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
                    baggs.add(b);//baggs�����̳ʿ� b�߰�
                    x += SPACE;//x��ġ�� 20��ŭ ���Ѵ�.
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
    	//������ �����쿡 �׸���.

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        
        header = new Header();
        header.UpdateSteps(getCurrentSteps());
        header.render(g);
        
        leftbaggs(g);
        coinCheck();
       
        currentScore = new Score();
        currentScore.render(g);
        g.drawString(String.valueOf(score), 640, 130);
        
        timer = new Timer();
        timer.render(g);
        
	    ArrayList<Actor> world = new ArrayList<>();
	    //world = ������ ��� ��ü�� ����
	    world.addAll(walls);
	    world.addAll(areas);
	    world.addAll(baggs);
	    world.addAll(coins);
	    world.add(soko);
	    world.add(enemy);
	    
	    for (int i = 0; i < world.size(); i++) {
	
	        Actor item = world.get(i);//�����̳ʵ��� �ݺ��ؼ� ������.
	
	        if (item instanceof Area) {
	            g.drawImage(item.getImage(), item.x() + 8, item.y() + 8, this);
	        } 
	        else if(item instanceof Player || item instanceof Enemy) {
	        	g.drawImage(item.getImage(), item.x() + 7, item.y() + 1, this);
	        }
	        else{
	            g.drawImage(item.getImage(), item.x(), item.y(), this);
	        }
	
	        if (isCompleted) {//������ ������, "Completed"�� ������ â ���� ��� �𼭸��� �׸���.
	            g.setColor(Color.WHITE);
	            g.drawString("Completed", 25, 20);
	        }
	        
	        if (isSokoDead) {
            	g.setColor(Color.WHITE);
            	g.drawString("You Died", 25, 20);
            }
	    }
	    
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //������Ʈ�� �׸����� �ϴ� ��������, ũ�Ⱑ ����ǰų� ��ġ�� ����ǰų� ������Ʈ�� �������� ���� ������� ��
        buildWorld(g);
    }
    
    private class TAdapter extends KeyAdapter {//Ű �Է¹ޱ�

        @Override
        public void keyPressed(KeyEvent e) {

            if (isCompleted) {
                return;
            }
            
            if (isSokoDead) {
            	return;
            }

            int key = e.getKeyCode();

            	 switch (key) {//� Ű�� ���������� Ȯ���Ѵ�. �츮�� cursorŰ�� ���� ���ڹݹ�ü�� �����Ѵ�.
             	//������ �����ٸ�, ���ڹ��� wall�̳� baggage�� �浹�ߴ��� üũ�Ѵ�. �浹���� �ʾҴٸ�, ���ڹ��� �������� �ű��.
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
                     
                 case KeyEvent.VK_R://RŰ�� ������ ������ ������Ѵ�.
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
    	//���ڹ��̳� baggage�� ���� ������� �ʵ��� ���������.
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
    	//baggage�� ��, ���ڹ�  ��ü �Ǵ� �ٸ� baggage�� �浹�� �� �ִ�. �ٸ� ��ü�� �浹���� �ʾƾ� baggage �ű�� ����
    	//baggage �ű涧�� isCompleted()�� ���� ������ �������� Ȯ���� ����.
        switch (type) {
            
            case LEFT_COLLISION:
                
                for (int i = 0; i < baggs.size(); i++) {

                    Baggage bag = baggs.get(i);

                    if (soko.isLeftCollision(bag)) {

                        for (int j = 0; j < baggs.size(); j++) {
                            
                            Baggage item = baggs.get(j);
                            
                            if (!bag.equals(item)) {//baggage�� baggage�� �浹�� ��
                                
                                if (bag.isLeftCollision(item)) {
                                    return true;
                                }
                            }
                            
                            if (checkWallCollision(bag, LEFT_COLLISION)) {//baggage�� ���� �浹�� ��
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
    	//������ �������� Ȯ���Ѵ�.
    	//baggage�� ���� ���� �� �ִ�. ��� baggage�� (x,y)��ǥ�� �������� ���Ѵ�.
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

        if (finishedBags == nOfBags) {//finishedBags�� nOfBags(���� �� baggage��)�� ���� �� ������ ����ȴ�.
            
            isCompleted = true;
            repaint();
        }
    }
    
    private void restartLevel() {//�����

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
	    	Coin coin = coins.get(i);
	    	if(soko.getRect().intersects(coin.getRect())){
	    		score += 10;
	    		currentScore.save(score);
	    		coins.remove(i);
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
    	g.setFont(new Font("�������",Font.PLAIN,22));
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
    
   
}
