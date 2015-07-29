package com.company;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by Jacob on 2015-07-27.
 */
public class Player {

    private int xPos, yPos, dx, dy, bombs, health, power, speed,
    left, right, up, down, placeBomb;
    private Image image;

    public Player(int player){
        this.speed = 1;
        this.bombs = 1;
        this.power = 1;
        this.health = 3;
        this.dx = 0;
        this.dy = 0;

        initializeControls(player);

        switch (player) {
            case 1:
                this.xPos = 1;
                this.yPos = 1;
            case 2:
                this.xPos = 5;
                this.yPos = 5;
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
            case 2:
                this.up = KeyEvent.VK_W;
                this.down = KeyEvent.VK_S;
                this.left = KeyEvent.VK_A;
                this.right = KeyEvent.VK_D;
                this.placeBomb = KeyEvent.VK_E;
        }
    }

    public void move() {
        this.xPos += dx;
        this.yPos += dy;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == left) {
            dx = -1;
        }
        if (key == right) {
            dx = 1;
        }
        if (key == up) {
            dy = -1;
        }
        if (key == down) {
            dy = 1;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == left) {
            dx = 0;
        }
        if (key == right) {
            dx = 0;
        }
        if (key == up) {
            dy = 0;
        }
        if (key == down) {
            dy = 0;
        }
    }

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
