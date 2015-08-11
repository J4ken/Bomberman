package com.company;

import javax.swing.text.Position;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by Håkan on 2015-08-11.
 */
public class Player1 extends AbstractPlayer {

    private static Player1 instance = null;

    protected Player1() {
        super();
        this.name = "Player 1";
        this.position.x = 1;
        this.position.y = 1;
        initializeControls(KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_SPACE);
    }

    public static Player1 getInstance() {
        if (instance == null) {
            instance = new Player1();
        }
        return instance;
    }
}
