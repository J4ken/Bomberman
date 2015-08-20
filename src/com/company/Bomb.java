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

    /*public void blowBomb() {
        Tiles t;
        //bombs.remove(b);
        java.util.List<Point> explosionPattern = new ArrayList<Point>();
        explosionPattern.add(new Point(this.getxPos(), this.getyPos()));
        //board.setTile(b.getxPos(), b.getyPos(), Tiles.FIRE);

        // lägger in FIRE-Tiles på alla platser där bomben exploderar
        for (int i = 0; i < power; ++i) {
            if (i == 0) continue;
            t = board.getTile(xPos, yPos + i);
            if (t != Tiles.WALL) {
                explosionPattern.add(new Point(xPos, yPos + i));
                switch (t) {

                    // ersätter FLOOR med FIRE
                    case FLOOR:
                        //        board.setTile(b.getxPos(), b.getyPos() + i, Tiles.FIRE);
                        break;

                    // om vi spränger en låda så ska loopen brytas och lådan försvinner
                    case BOX:
                        //      board.setTile(b.getxPos(), b.getyPos() + i, Tiles.FIRE);
                        // fixa så att en powerup kommer fram
                        i = power;
                        break;

                    // om vi spränger en bomb så ska den explodera
                    case BOMB:
                        //    board.setTile(b.getxPos(), b.getyPos() + i, Tiles.FIRE);
                        for (Bomb bomb : Game.bombs) {
                            if (bomb.getxPos() == xPos && bomb.getyPos() == yPos + i) {
                                bomb.blow();
                                break;
                            }
                        }

                        // om det redan ligger en FIRE-Tile så ska vi lägga till den i undantagsllistan
                    case FIRE:
                        Point p = new Point(xPos, yPos);
                        Game.dontRemove.add(p);
                }
            }
        }

        for (int i = 0; i < power; ++i) {
            if (i == 0) continue;
            t = board.getTile(xPos, yPos - i);
            if (t != Tiles.WALL) {
                explosionPattern.add(new Point(xPos, yPos - i));
                switch (t) {
                    case FLOOR:
                        //        board.setTile(b.getxPos(), b.getyPos() - i, Tiles.FIRE);
                        break;
                    case BOX:
                        //      board.setTile(b.getxPos(), b.getyPos() - i, Tiles.FIRE);
                        // fixa så att en powerup kommer fram
                        i = power;
                        break;
                    case BOMB:
                        //    board.setTile(b.getxPos(), b.getyPos() - i, Tiles.FIRE);
                        for (Bomb bomb : Game.bombs) {
                            if (bomb.getxPos() == xPos && bomb.getyPos() == yPos - i) {
                                bomb.blow();
                                break;
                            }
                        }
                    case FIRE:
                        Point p = new Point(xPos, yPos - i);
                        Game.dontRemove.add(p);
                }
            }
        }

        for (int i = 0; i < power; ++i) {
            if (i == 0) continue;
            t = board.getTile(xPos + i, yPos);
            if (t != Tiles.WALL) {
                explosionPattern.add(new Point(xPos + i, yPos));
                switch (t) {
                    case FLOOR:
                        //        board.setTile(b.getxPos() + i, b.getyPos(), Tiles.FIRE);
                        break;
                    case BOX:
                        //      board.setTile(b.getxPos() + i, b.getyPos(), Tiles.FIRE);
                        // fixa så att en powerup kommer fram
                        i = power;
                        break;
                    case BOMB:
                        //    board.setTile(b.getxPos() + i, b.getyPos(), Tiles.FIRE);
                        for (Bomb bomb : Game.bombs) {
                            if (bomb.getxPos() == xPos + i && bomb.getyPos() == yPos) {
                                bomb.blow();
                                break;
                            }
                        }
                    case FIRE:
                        Point p = new Point(xPos + i, yPos);
                        Game.dontRemove.add(p);
                }
            }
        }

        for (int i = 0; i < power; ++i) {
            if (i == 0) continue;
            t = board.getTile(xPos - i, yPos);
            if (t != Tiles.WALL) {
                explosionPattern.add(new Point(xPos - i, yPos));
                switch (t) {
                    case FLOOR:
//                    board.setTile(b.getxPos() - i, b.getyPos(), Tiles.FIRE);
                        break;
                    case BOX:
                        //                  board.setTile(b.getxPos() - i, b.getyPos(), Tiles.FIRE);
                        // fixa så att en powerup kommer fram
                        i = power;
                        break;
                    case BOMB:
                        //                board.setTile(b.getxPos() - i, b.getyPos(), Tiles.FIRE);
                        for (Bomb bomb : Game.bombs) {
                            if (bomb.getxPos() == xPos - i && bomb.getyPos() == yPos) {
                                bomb.blow();
                                break;
                            }
                        }
                    case FIRE:
                        Point p = new Point(xPos - i, yPos);
                        Game.dontRemove.add(p);
                }
            }
        }

        java.util.List<Point> explosionPatternCopy = new ArrayList<Point>(explosionPattern);
        for (Point p : explosionPatternCopy) {
            board.setTile(p.x, p.y, Tiles.FIRE);
        }

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        for (Point p : explosionPatternCopy) {
            board.setTile(p.x, p.y, Tiles.FLOOR);
        }
    }*/
}
