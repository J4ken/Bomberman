package com.company;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Håkan on 2015-08-11.
 */
public abstract class AbstractPlayer {
    protected int bombs;
    protected int bombcount;
    protected int health;
    protected int power;
    protected int speed;
    protected int left;
    protected int right;
    protected int up;
    protected int down;
    protected int placeBomb;
    protected int score;
    protected String name;
    protected PlayerAction action;
    protected Image image;
    protected Point position = new Point(0,0);
    protected boolean droppingBomb, moving, winner;
    protected Set<Integer> controls = new HashSet<Integer>(5);

    public AbstractPlayer(){
        score = 0;
        speed = 1;
        bombs = 1;
        bombcount = 0;
        power = 1;
        health = 3;
        name = "";
        droppingBomb = false;
        moving = false;
        action = PlayerAction.STAND;
        winner = false;
    }

    public void initializeControls(int up, int down, int left, int right, int placeBomb) {
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
        this.placeBomb = placeBomb;
        controls.add(up);
        controls.add(down);
        controls.add(left);
        controls.add(right);
        controls.add(placeBomb);
    }

    public void movePlayer() {
        if (!moving) {
            switch (action) {
                case UP:
                    position.y -= 1;
                    break;
                case DOWN:
                    position.y += 1;
                    break;
                case LEFT:
                    position.x -= 1;
                    break;
                case RIGHT:
                    position.x += 1;
                    break;
            }
            action = PlayerAction.STAND;
        }
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        moving = true;
        if (key == left) action = PlayerAction.LEFT;
        if (key == right) action = PlayerAction.RIGHT;
        if (key == up) action = PlayerAction.UP;
        if (key == down) action = PlayerAction.DOWN;
        if (key == placeBomb) droppingBomb = true;
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        moving = false;
        droppingBomb = false;
        /*if (key == left) action = PlayerAction.STAND;
        if (key == right) action = PlayerAction.STAND;
        if (key == up) action = PlayerAction.STAND;
        if (key == down) action = PlayerAction.STAND;
        if (key == placeBomb) action = PlayerAction.BOMB;
    */
    }

    public int getScore() {
        return score;
    }

    public void increaseScore() {
        this.score += 1;
    }

    public void setAction(PlayerAction a) {
        action = a;
    }

    public void setWinner() {
        winner = true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PlayerAction getAction() {
        return action;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(int x, int y) {
        this.position.x = x;
        this.position.y = y;
    }

    public boolean isDroppingBomb() {
        return droppingBomb;
    }

    public void setDroppingBomb(boolean droppingBomb) {
        this.droppingBomb = droppingBomb;
    }

    public void loseHealth() {
        health -= 1;
    }

    public int getBombs() {
        return bombs;
    }

    public void increaseBombs() {
        System.out.print("increae Bomb");
        bombs += 1;
    }

    public int getHealth() {
        return health;
    }

    public void increaseHealth() {
        health += 1;
    }

    public int getPower() {
        return power;
    }

    public void increasePower() {
        System.out.print("increase Power");
        power += 1;
    }

    public int getSpeed() {
        return speed;
    }

    public void increaseSpeed() {
        System.out.print("increase Speed");
        speed += 1;
    }

    public int getBombcount() {
        return bombcount;
    }

    public void increaseBombcount() {
        this.bombcount += 1;
    }

    public void decreaseBombcount(){
        this.bombcount -= 1;
    }
}
