package com.company;

/**
 * Created by Jacob on 2015-07-27.
 */
public class Board {

    private Tiles[][] board;

    public Board (int height, int width){
        this.board = new Tiles[height][width];
        height = height;
        width = width;

        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                if (i == 0 || j == 0 || i == height-1 || j == width-1 || ((i % 2 == 0) && (j % 2 == 0))) {
                    board[i][j] = Tiles.WALL;
                }
                else {
                    board[i][j] = Tiles.FLOOR;
                }
            }
        }
    }
}
