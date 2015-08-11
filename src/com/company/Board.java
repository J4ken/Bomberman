package com.company;

import java.util.Random;

/**
 * Created by Jacob on 2015-07-27.
 */
public class Board {

    private Tiles[][] board;
    private int width, height;
    private Random rnd = new Random();

    public Board (int height, int width){
        board = new Tiles[height][width];
        this.height = height;
        this.width = width;

        newBoard();
    }

    public void newBoard() {
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                if (i < 2) board[i][j] = Tiles.WALL;
                else if (i == 0 || j == 0 || i == height-1 || j == width-1 || ((i % 2 != 0) && (j % 2 == 0))) {
                    board[i][j] = Tiles.WALL;
                }
                else if (rnd.nextInt(10) < 5 && i+j > 3 && i+j < height+width-5) {
                    board[i][j] = Tiles.BOX;
                }
                else board[i][j] = Tiles.FLOOR;
            }
        }
    }

    public void setTile (int xPos, int yPos, Tiles t) {
        board[yPos][xPos] = t;
    }

    public Tiles getTile(int x, int y) {
        return board[y][x];
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
