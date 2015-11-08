package com.company;

import java.awt.event.KeyEvent;

/**
 * Created by Håkan on 2015-08-11.
 */
public class Player2 extends AbstractPlayer{
    /**
     * These variables are self explaning
     */
    public static final int PLAYER2_START_XPOS = 13;
    public static final int PLAYER2_START_YPOS = 12;

    /**
     *Player 2 is used as a singleton designpattern.
     *The program can only create one instance of Player 2
     *
     */

    protected Player2() {
        super("Player2");
        this.position.x = PLAYER2_START_XPOS;
        this.position.y = PLAYER2_START_YPOS;
        initializeControls(KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_E);
    }


    /**
     *
     */
    private static final class InstanceHolder
    {
        private static final Player2 instance = new Player2();
    }

    public static Player2 getInstance() {
        return InstanceHolder.instance;
    }
}
