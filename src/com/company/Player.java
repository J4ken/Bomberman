package com.company;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Jacob on 2015-07-27.
 */
public class Player {

    private int bombs, health, power, speed,
    left, right, up, down, placeBomb;
    private PlayerAction action;
    private Image image;
    Point position;
    Set<Integer> controls = new HashSet<Integer>(5);

    public Player(int playerID){
        speed = 1;
        bombs = 1;
        power = 1;
        health = 3;
        action = PlayerAction.STAND;

        initializeControls(playerID);

        switch (playerID) {
            case 1:
                position = new Point(1, 1);
                break;
            case 2:
                position = new Point(5, 5);
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
        controls.add(up);
        controls.add(down);
        controls.add(left);
        controls.add(right);
        controls.add(placeBomb);
    }

    public void movePlayer() {
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
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == left) action = PlayerAction.LEFT;
        if (key == right) action = PlayerAction.RIGHT;
        if (key == up) action = PlayerAction.UP;
        if (key == down) action = PlayerAction.DOWN;
        if (key == placeBomb) action = PlayerAction.BOMB;
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == left) action = PlayerAction.STAND;
        if (key == right) action = PlayerAction.STAND;
        if (key == up) action = PlayerAction.STAND;
        if (key == down) action = PlayerAction.STAND;
        if (key == placeBomb) action = PlayerAction.BOMB;
    }

    public void setAction(PlayerAction a) {
        action = a;
    }

    public PlayerAction getAction() {
        return action;
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
