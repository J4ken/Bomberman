package com.company;

/**
 * Created by Håkan on 2015-07-30.
 */
public class Bomb {
    final static int timer = 3000;
    private  int xPos, yPos, power;
    Board board;

    public Bomb(Player p, Board b) {
        this.xPos = p.getxPos();
        this.yPos = p.getyPos();
        this.power = 1;
        this.board = b;
    }

    public void blow(Bomb bomb) {
        for (int i = 0; i < power; ++i) {
            for (int j = 0; j < power; ++j) {
                if (board.getTile(i, j) == Tiles.FLOOR) {

                }
            }
        }
    }

}
