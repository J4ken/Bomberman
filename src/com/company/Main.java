package com.company;

public class Main {

    public static void main(String[] args) {
        int h = 11;
        int w = 25;

        Player player = new Player(1);
	    Board board = new Board(h,w);
        board.insertPlayer(player.xPos,player.yPos);

        board.printBoard();

    }

}
