package com.company;

public class Main {

    public static void main(String[] args) {
	    Board board = new Board(15,17);
        Player player_1 = new Player(0,0,10,1,1);

        System.out.println(player_1.x);
        System.out.println(board.height);
    }

}
