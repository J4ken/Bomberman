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
        speed = 1;
        bombs = 1;
        power = 1;
        health = 3;

        initializeControls(player);

        switch (player) {
            case 1:
                xPos = 1;
                yPos = 1;
                action = Action.STAND;
                break;
            case 2:
                xPos = 5;
                yPos = 5;
                action = Action.STAND;
                break;
        }
    }

    private void initializeControls(int player) {
        switch (player) {
            case 1:
                up = KeyEvent.VK_UP;
                down = KeyEvent.VK_DOWN;
                left = KeyEvent.VK_LEFT;
                right = KeyEvent.VK_RIGHT;
                placeBomb = KeyEvent.VK_SPACE;
                break;
            case 2:
                up = KeyEvent.VK_W;
                down = KeyEvent.VK_S;
                left = KeyEvent.VK_A;
                right = KeyEvent.VK_D;
                placeBomb = KeyEvent.VK_E;
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

    public void setAction(Action a) {
        action = a;
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
