package com.company;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/*  Game is...

    Game can reach board
    Game can reach Player

    Game can check collision
    Game is mainloop

    Game can set bomb
    Game can set gameOver




 */


/**
 * Created by Håkan on 2015-07-28.
 */
public class Game {

    private Player player1, player2;
    private Board board;

    public boolean gameover = false;



    public Game() {
        this.board = new Board(7, 7);
        this.player1 = new Player(1);
        this.player2 = new Player(2);
    }

    // main loop function
    public void tick(){

        System.out.println("One frame");

    }


}
