package com.company;

/**
 * Created by Jacob on 2015-07-27.
 */
public class Player {

    private int xPos, yPos, bombs, health, power, speed;

    public Player(int player){
        this.speed = 1;
        this.bombs = 1;
        this.power = 1;
        this.health = 3;

        switch (player) {
            case 1:
                this.xPos = 1;
                this.yPos = 1;
            case 2:
                this.xPos = 5;
                this.yPos = 5;
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
