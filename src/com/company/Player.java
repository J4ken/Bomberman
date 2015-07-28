package com.company;

/**
 * Created by Jacob on 2015-07-27.
 */
public class Player {

    static int xPos, yPos, bombs, health, power, speed;

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

    public static int getxPos() {
        return xPos;
    }

    public static void setxPos(int xPos) {
        Player.xPos = xPos;
    }

    public static int getyPos() {
        return yPos;
    }

    public static void setyPos(int yPos) {
        Player.yPos = yPos;
    }

    public static int getBombs() {
        return bombs;
    }

    public static void setBombs(int bombs) {
        Player.bombs = bombs;
    }

    public static int getHealth() {
        return health;
    }

    public static void setHealth(int health) {
        Player.health = health;
    }

    public static int getPower() {
        return power;
    }

    public static void setPower(int power) {
        Player.power = power;
    }

    public static int getSpeed() {
        return speed;
    }

    public static void setSpeed(int speed) {
        Player.speed = speed;
    }
}
