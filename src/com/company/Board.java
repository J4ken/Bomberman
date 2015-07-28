package com.company;

/**
 * Created by Jacob on 2015-07-27.
 */
public class Board {

    private String [][] board;
    private int height;
    private int width;

    public int xPos;
    public int yPos;


    public Board (int height, int width){
        this.height = height;
        this.width = width;
        this.board = new String[height][width];


        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                if (i == 0 || j == 0 || i == height-1 || j == width-1 || ((i % 2 == 0) && (j % 2 == 0))) {
                    board[i][j] = "#";
                }
                else {
                    board[i][j] = "O";
                }
            }
        }
    }
    public void insertPlayer(int xPos, int yPos){

        board[xPos][yPos] = "P";
    }


    public void printBoard (){

        for(int i = 0; i < height; ++i){
            for(int j = 0; j < width; ++j){
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
    }
}
