package com.zetcode;

import java.awt.Image;
import java.awt.Rectangle;
import java.io.Serializable;

public class Actor implements Serializable{

	private static final long serialVersionUID = 1L;

	private final int SPACE = 32;

    private int x;
    private int y;
    private Image image;

    public Actor(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image img) {
        image = img;
    }

    public int x() {
        
        return x;
    }

    public int y() {
        
        return y;
    }

    public void setX(int x) {
        
        this.x = x;
    }

    public void setY(int y) {
        
        this.y = y;
    }

    public boolean isLeftCollision(Actor actor) {
    	//actor가 왼쪽으로 다른 actor(wall,baggage,sokoban)과 충돌하는지 확인한다.
        return x() - SPACE == actor.x() && y() == actor.y();
    }

    public boolean isRightCollision(Actor actor) {
        
        return x() + SPACE == actor.x() && y() == actor.y();
    }

    public boolean isTopCollision(Actor actor) {
        
        return y() - SPACE == actor.y() && x() == actor.x();
    }

    public boolean isBottomCollision(Actor actor) {
        
        return y() + SPACE == actor.y() && x() == actor.x();
    }
    
    public Rectangle getRect() {
    	return new Rectangle(x(),y(),32,32);
    }
}
