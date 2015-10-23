package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by Håkan on 2015-07-30.
 */
public class Bomb {
    
    /*

     */

    final static int DELAY = 3000;
    private  int xPos, yPos, power;
    private boolean explode;
    private final Timer bombTimer;
    private String player;


    public Bomb(AbstractPlayer p) {
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
        bombTimer = new Timer(DELAY, blowBomb);
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
