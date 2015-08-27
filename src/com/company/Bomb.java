package com.company;

import javax.swing.*;
import javax.swing.Action;
import javax.swing.Timer;
import java.awt.*;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.util.*;

/**
 * Created by Håkan on 2015-07-30.
 */
public class Bomb {
    final static int delay = 3000;
    private  int xPos, yPos, power;
    private boolean explode;
    private Board board;
    private final Timer bombTimer;
    private String player;


    public Bomb(AbstractPlayer p) {
        this.board = board;
        xPos = p.position.x;
        yPos = p.position.y;
        power = p.getPower();
        explode = false;
        player = p.name;


        final Action blowBomb = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                bombTimer.stop();
                blow();
            }
        };
        bombTimer = new Timer(delay, blowBomb);
        bombTimer.setCoalesce(true);
        bombTimer.start();
    }

    public boolean isExplode() {
        return explode;
    }

    public int getPower() {
        return power;
    }

    public int getyPos() {
        return yPos;
    }

    public int getxPos() {
        return xPos;
    }

    public String getPlayer(){return player;}

    public void blow() {
        explode = true;
    }
}
