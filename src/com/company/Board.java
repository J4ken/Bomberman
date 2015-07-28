package com.company;

/**
 * Created by Jacob on 2015-07-27.
 */
public class Board {

    private Tiles[][] board;
    private int width, height;

    public Board (int height, int width){
        this.board = new Tiles[height][width];
        this.height = height;
        this.width = width;

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

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
