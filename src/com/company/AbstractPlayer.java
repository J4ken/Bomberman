package com.company;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Collection;
import java.util.HashSet;

/**
 * Created by Håkan on 2015-08-11.
 */
public class AbstractPlayer {

    /*
     Abstracy player is used to create new player objects.
     */

    protected boolean damaged = false;
    protected int bombs;
    protected int bombcount;
    protected int power;
    protected int speed;
    protected int left;
    protected int right;
    protected int up;
    protected int down;
    protected int placeBomb;
    protected int score;
    protected String name = null;
    protected PlayerAction action;
    protected Point position = new Point(0,0);
    protected boolean droppingBomb, moving;
    protected Collection<Integer> controls = new HashSet<>(5);

    protected AbstractPlayer(){
        score = 0;
        speed = 1;
        bombs = 1;
        bombcount = 0;
        power = 1;
        droppingBomb = false;
        moving = false;
        action = PlayerAction.PLAYER_STAND;
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
            //this is only for checking movemnt and not when you drop a bomb
            switch (action) {
                case PLAYER_UP:
                    position.y -= 1;
                    break;
                case PLAYER_DOWN:
                    position.y += 1;
                    break;
                case PLAYER_LEFT:
                    position.x -= 1;
                    break;
                case PLAYER_RIGHT:
                    position.x += 1;
                    break;
                case PLAYER_BOMB:
                    droppingBomb = true;
                case PLAYER_STAND:
                    break;
            }
            action = PlayerAction.PLAYER_STAND;
        }
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        moving = true;
        if (key == left) action = PlayerAction.PLAYER_LEFT;
        if (key == right) action = PlayerAction.PLAYER_RIGHT;
        if (key == up) action = PlayerAction.PLAYER_UP;
        if (key == down) action = PlayerAction.PLAYER_DOWN;
        if (key == placeBomb) action = PlayerAction.PLAYER_BOMB;
    }

    public void keyReleased() {
        moving = false;
        droppingBomb = false;
    }

    public int getScore() {
        return score;
    }

    public void increaseScore(){
        score += 1;
    }

    public String getName() {
        return name;
    }

    public PlayerAction getAction() {
        return action;
    }

    public boolean isDroppingBomb() {
        return droppingBomb;
    }

    public void setDroppingBomb(boolean droppingBomb) {
        this.droppingBomb = droppingBomb;
    }

    public void increaseBombs() {
        bombs += 1;
    }

    public int getPower() {
        return power;
    }

    public void increasePower() {
        power += 1;
    }


    public void increaseSpeed() {
        speed += 1;
    }

    public void increaseBombcount() {
        this.bombcount += 1;
    }

    public void decreaseBombcount(){
        this.bombcount -= 1;
    }
}
