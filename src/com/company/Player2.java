package com.company;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by Håkan on 2015-08-11.
 */
public class Player2 extends AbstractPlayer{

    private static Player2 instance = null;

    protected Player2() {
        super();
        this.name = "Player 2";
        this.position.x = 10;
        this.position.y = 10;
        initializeControls(KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_E);
    }

    public static Player2 getInstance() {
        if (instance == null) {
            instance = new Player2();
        }
        return instance;
    }
}
