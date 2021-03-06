package com.company;

import java.awt.event.KeyEvent;

/**
 * Created by H�kan on 2015-08-11.
 */
public class Player1 extends AbstractPlayer {

    /*
     Player 1 is used as a singleton designpattern.
     The program can only create one instance of Player 1
     */

    protected Player1() {
        super("Player1");
        this.position.x = 1;
        this.position.y = 2;

        initializeControls(KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_SPACE);
    }

    private static final class InstanceHolder
    {
        //instance is a goodenough name for this usage!
        private static final Player1 instance = new Player1();
    }

    public static Player1 getInstance() {
        return InstanceHolder.instance;
    }
}
