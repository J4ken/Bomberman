package com.company;

import java.awt.event.KeyEvent;

/**
 * Created by Håkan on 2015-08-11.
 */
public class Player1 extends AbstractPlayer {

    /*
     Player 1 is used as a singleton designpattern.
     The program can only create one instance of Player 1
     */

    protected Player1() {
        this.name = "Player 1"; // assign the name for the player in the AbstractPlayer class
        this.position.x = 1;
        this.position.y = 2;

        initializeControls(KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_SPACE);
    }

    private static final class InstanceHolder
    {
        @SuppressWarnings("ConstantNamingConvention") private static final Player1 instance = new Player1();
    }

    public static Player1 getInstance() {
        return InstanceHolder.instance;
    }
}
