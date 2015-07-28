package com.company;

/**
 * Created by Jacob on 2015-07-27.
 */
public class Player {

    static int xPos, yPos, bombs, power;

    public Player(int player){
        //this.speed = speed; ??
        this.bombs = 1;
        this.power = 1;

        switch (player) {
            case 1:
                this.xPos = 1;
                this.yPos = 1;
            case 2:
                this.xPos = 5;
                this.yPos = 5;
        }
    }

}
