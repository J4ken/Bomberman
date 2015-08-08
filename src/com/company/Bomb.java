package com.company;

import javax.swing.*;
import javax.swing.Action;
import java.awt.event.ActionEvent;

/**
 * Created by Håkan on 2015-07-30.
 */
public class Bomb {
    final static int delay = 3000;
    private  int xPos, yPos, power;
    private boolean explode;

    private final Timer bombTimer;

    public Bomb(Player p) {
        xPos = p.position.x;
        yPos = p.position.y;
        power = 2;
        explode = false;

        final Action blowBomb = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
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

    void blow() {
        explode = true;
        System.out.println("BLOW!");
    }

}
