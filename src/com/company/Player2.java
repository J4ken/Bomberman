package com.company;

import java.awt.event.KeyEvent;

/**
 * Created by Håkan on 2015-08-11.
 */
public class Player2 extends AbstractPlayer{

      /*
     Player 2 is used as a singleton designpattern.
     The program can only create one instance of Player 2
     */

    protected Player2() {
        this.name = "asasasd"; // assign playername for player 2
        this.position.x = 13;  //starting position for player2
        this.position.y = 12; //staring position for player2
        initializeControls(KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_E);
    }

    private static final class InstanceHolder
    {
        @SuppressWarnings("ConstantNamingConvention") private static final Player2 instance = new Player2();
    }

    public static Player2 getInstance() {
        return InstanceHolder.instance;
    }
}
