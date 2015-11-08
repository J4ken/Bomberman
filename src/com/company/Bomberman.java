package com.company;

/**
 * Bomberman initialize the game by creating a new board and starting a new game
 */

public final class Bomberman
{
    private Bomberman() {}

    /*
     * Bomberman function to create a new board and start the game
     */


    public static void main(String[] args) { // main does not have to be private
        final int boardHeight = 14;
        final int boardWidth = 15;
        Board board = new Board(boardHeight, boardWidth);
        //since game is the program, we wont have to set game to anything
        new Game(board);
    }

}
