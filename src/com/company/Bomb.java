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
        this.xPos = p.getxPos();
        this.yPos = p.getyPos();
        this.power = 1;
        this.explode = false;

        final Action blowBomb = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                blow();
            }
        };
        this.bombTimer = new Timer(delay, blowBomb);
        bombTimer.setCoalesce(true);
        bombTimer.start();
    }

    void blow() {
        explode = true;
    }

}
