package com.company;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Håkan on 2015-07-28.
 */
public class Game implements KeyListener {
    private Player player1, player2;
    private Board board;

    public Game() {
        this.board = new Board(7, 7);
        this.player1 = new Player(1);
        this.player2 = new Player(2);
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        switch (key) {
            case KeyEvent.VK_LEFT:
                if (board.getTile(player1.getyPos(), player1.getxPos()-1) == Tiles.FLOOR) {
                    player1.setxPos(player1.getxPos()-1);
                }
            case KeyEvent.VK_RIGHT:
                if (board.getTile(player1.getyPos(), player1.getxPos()+1) == Tiles.FLOOR) {
                    player1.setxPos(player1.getxPos()+1);
                }
            case KeyEvent.VK_UP:
                if (board.getTile(player1.getyPos()+1, player1.getxPos()) == Tiles.FLOOR) {
                    player1.setyPos(player1.getyPos() + 1);
                }
            case KeyEvent.VK_DOWN:
                if (board.getTile(player1.getyPos()-1, player1.getxPos()) == Tiles.FLOOR) {
                    player1.setyPos(player1.getyPos()-1);
                }
            case KeyEvent.VK_SPACE:
                //släpp bomb
            case KeyEvent.VK_A:
                if (board.getTile(player2.getyPos(), player2.getxPos()-1) == Tiles.FLOOR) {
                    player2.setxPos(player2.getxPos()-1);
                }
            case KeyEvent.VK_D:
                if (board.getTile(player2.getyPos(), player2.getxPos()+1) == Tiles.FLOOR) {
                    player2.setxPos(player2.getxPos() + 1);
                }
            case KeyEvent.VK_W:
                if (board.getTile(player2.getyPos()+1, player2.getxPos()) == Tiles.FLOOR) {
                    player2.setyPos(player2.getyPos() + 1);
                }
            case KeyEvent.VK_S:
                if (board.getTile(player2.getyPos()-1, player2.getxPos()) == Tiles.FLOOR) {
                    player2.setyPos(player2.getyPos()-1);
                }
            case KeyEvent.VK_E:
                //släpp bomb

        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
