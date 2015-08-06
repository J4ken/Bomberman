package com.company;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Håkan on 2015-07-28.
 */
public class Game {
    private Player player1, player2;
    private Board board;

    public Game() {
        board = new Board(7, 7);
        player1 = new Player(1);
        player2 = new Player(2);
    }
}
