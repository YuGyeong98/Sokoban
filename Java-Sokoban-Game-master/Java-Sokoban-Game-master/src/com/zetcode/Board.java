package com.zetcode;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JPanel;
//게임에 저장이 되어야 할 데이터들을 여기에 저장한다.
public class Board extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	private final int OFFSET = 30;//윈도우 창의 테두리와 게임 사이의 가로 거리
    private final int SPACE = 20;//20*20px(벽 이미지 사이즈)
   
    private final int LEFT_COLLISION = 1;//왼쪽 충돌
    private final int RIGHT_COLLISION = 2;//오른쪽 충돌
    private final int TOP_COLLISION = 3;//상단 충돌
    private final int BOTTOM_COLLISION = 4;//하단 충돌 
  
//ArrayList - 크기가 가변적(자동적)으로 변하는 선형리스트/ ArrayList<타입설정>
    private ArrayList<Wall> walls;//walls를 담을 수 있는 컨테이너
    private ArrayList<Baggage> baggs;//baggs를 담을 수 있는 컨테이너
    private ArrayList<Area> areas;//areas를 담을 수 있는 컨테이너
    
    private Player soko;
    private int w = 0;//width
    private int h = 0;//height
    
    private boolean isCompleted = false;//실패 
//#=벽(wall), $=이동할 상자(baggage), .=우리가 박스를 옮겨야 할 장소(area), @=소코반(sokoban)
    private String level =
              "    ######\n"
            + "    ##   #\n"
            + "    ##$  #\n"
            + "  ####  $##\n"
            + "  ##  $ $ #\n"
            + "#### # ## #   ######\n"
            + "##   # ## #####  ..#\n"
            + "## $  $          ..#\n"
            + "###### ### #@##  ..#\n"
            + "    ##     #########\n"
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
    	//게임을 진행하면서  walls,baggs,areas컨테이너를 채운다.
    	
        //컨테이너
        walls = new ArrayList<>();//new에서 타입 생략 가능/ Wall객체들만 사용 가능
        baggs = new ArrayList<>();
        areas = new ArrayList<>();

        int x = OFFSET;
        int y = OFFSET;

        Wall wall;
        Baggage b;
        Area a;

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

                case '@':
                    soko = new Player(x, y);
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

        g.setColor(new Color(250, 240, 170));
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        //좌표 (0,0)에 크기만큼 사각형을 그린다.
        ArrayList<Actor> world = new ArrayList<>();
        //world = 게임의 모든 객체를 포함
        world.addAll(walls);
        world.addAll(areas);
        world.addAll(baggs);
        world.add(soko);
        //컨테이너를 통해 반복해서 물체를 그린다. 플레이어와 baggage이미지들은 조금 작아서 좌표에 2px를 더해서 중심을 잡는다.
        for (int i = 0; i < world.size(); i++) {

            Actor item = world.get(i);//컨테이너들을 반복해서 빼낸다.

            if (item instanceof Player || item instanceof Baggage) {
                
                g.drawImage(item.getImage(), item.x() + 2, item.y() + 2, this);
            } else {
                
                g.drawImage(item.getImage(), item.x(), item.y(), this);
            }

            if (isCompleted) {//레벨이 끝나면, "Completed"를 윈도우 창 왼쪽 상단 모서리에 그린다.
                
                g.setColor(new Color(0, 0, 0));
                g.drawString("Completed", 25, 20);
            }

        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);//super는 부모 클래스로부터 상속받은 필드나 메소드를 자식 클래스에서 참조하는 데 사용하는 참조 변수
        //컴포넌트가 그리져야 하는 시점마다, 크기가 변경되거나 위치가 변경되거나 컴포넌트가 가려졌던 것이 사라지는 등
        buildWorld(g);
    }

    private class TAdapter extends KeyAdapter {//키 입력받기

        @Override
        public void keyPressed(KeyEvent e) {

            if (isCompleted) {
                return;
                //1. 되돌린다
                //어셈블러 수준에서 코드의 수행순서가 결정되는 부분
                //2. 아무것도 없는것을
                //스택에 어떤 값도 저장하지 않는것을 의미
                //3. 문장의 끝
                //컴파일러에게 문장이 끝났으니 새로운 문장의 해석을 준비할것을 알려준다.
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
                    
                    break;
                    
                case KeyEvent.VK_RIGHT:
                    
                    if (checkWallCollision(soko, RIGHT_COLLISION)) {
                        return;
                    }
                    
                    if (checkBagCollision(RIGHT_COLLISION)) {
                        return;
                    }
                    
                    soko.move(SPACE, 0);
                    
                    break;
                    
                case KeyEvent.VK_UP:
                    
                    if (checkWallCollision(soko, TOP_COLLISION)) {
                        return;
                    }
                    
                    if (checkBagCollision(TOP_COLLISION)) {
                        return;
                    }
                    
                    soko.move(0, -SPACE);
                    
                    break;
                    
                case KeyEvent.VK_DOWN:
                    
                    if (checkWallCollision(soko, BOTTOM_COLLISION)) {
                        return;
                    }
                    
                    if (checkBagCollision(BOTTOM_COLLISION)) {
                        return;
                    }
                    
                    soko.move(0, SPACE);
                    
                    break;
                    
                case KeyEvent.VK_R://R키를 누르면 레벨을 재시작한다.
                    
                    restartLevel();
                    
                    break;
                    
                default:
                    break;
            }

            repaint();//화면이 다시 그려지도록 요청
        }
    }

    private boolean checkWallCollision(Actor actor, int type) {
    	//소코반이나 baggage가 벽을 통과하지 않도록 만들어졌다.
        switch (type) {
            
            case LEFT_COLLISION:
                
                for (int i = 0; i < walls.size(); i++) {
                    
                    Wall wall = walls.get(i);
                    
                    if (actor.isLeftCollision(wall)) {
                        
                        return true;//성공
                    }
                }
                
                return false;//실패
                
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

        if (isCompleted) {
            isCompleted = false;
        }
    }
}
