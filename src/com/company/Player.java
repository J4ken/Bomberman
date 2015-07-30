package com.company;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by Jacob on 2015-07-27.
 */
public class Player {

    private int xPos, yPos, bombs, health, power, speed,
    left, right, up, down, placeBomb;
    private Action action;
    private Image image;

    public Player(int player){
        this.speed = 1;
        this.bombs = 1;
        this.power = 1;
        this.health = 3;

        initializeControls(player);

        switch (player) {
            case 1:
                this.xPos = 1;
                this.yPos = 1;
                break;
            case 2:
                this.xPos = 5;
                this.yPos = 5;
                break;
        }
    }

    private void initializeControls(int player) {
        switch (player) {
            case 1:
                this.up = KeyEvent.VK_UP;
                this.down = KeyEvent.VK_DOWN;
                this.left = KeyEvent.VK_LEFT;
                this.right = KeyEvent.VK_RIGHT;
                this.placeBomb = KeyEvent.VK_SPACE;
                break;
            case 2:
                this.up = KeyEvent.VK_W;
                this.down = KeyEvent.VK_S;
                this.left = KeyEvent.VK_A;
                this.right = KeyEvent.VK_D;
                this.placeBomb = KeyEvent.VK_E;
                break;
        }
    }

    public void move() {
        switch (action) {
            case UP:
                yPos -= 1;
                break;
            case DOWN:
                yPos += 1;
                break;
            case LEFT:
                xPos -= 1;
                break;
            case RIGHT:
                xPos += 1;
                break;
        }
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == left) action = Action.LEFT;
        if (key == right) action = Action.RIGHT;
        if (key == up) action = Action.UP;
        if (key == down) action = Action.DOWN;
        if (key == placeBomb) action = Action.BOMB;
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == left) action = Action.STAND;
        if (key == right) action = Action.STAND;
        if (key == up) action = Action.STAND;
        if (key == down) action = Action.STAND;
        if (key == placeBomb) action = Action.BOMB;
    }

    public Action getAction() { return action; }

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public int getBombs() {
        return bombs;
    }

    public void setBombs(int bombs) {
        this.bombs = bombs;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
