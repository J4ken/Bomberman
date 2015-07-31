package com.company;

import javafx.scene.paint.*;
import sun.plugin2.util.ColorUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.util.AbstractMap;
import java.util.EnumMap;

/**
 * Created by Håkan on 2015-07-31.
 */
public class GraphicsComponent extends JComponent {
    private Board board;
    private AbstractMap<Tiles, Color> enumMap;
    final static int TILE_SIZE = 30;

    public GraphicsComponent(Board board) {
        this.board = board;
        setColorMap();

    }

    private void setColorMap() {
        enumMap = new EnumMap<Tiles, Color>(Tiles.class);
        enumMap.put(Tiles.BOMB, Color.BLACK);
        enumMap.put(Tiles.BOX, Color.BLUE);
        enumMap.put(Tiles.FIRE, Color.ORANGE);
        enumMap.put(Tiles.WALL, Color.DARK_GRAY);
        enumMap.put(Tiles.FLOOR, Color.GREEN);
        enumMap.put(Tiles.PLAYER1, Color.CYAN);
        enumMap.put(Tiles.PLAYER2, Color.RED);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        final Graphics2D g2d = (Graphics2D) g;

        for (int i = 0; i < board.getHeight(); ++i) {
            for (int j = 0; j < board.getWidth(); ++j) {
                g2d.setColor(enumMap.get(board.getTile(i, j)));
                g2d.fillRect(i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(TILE_SIZE * board.getWidth(), TILE_SIZE * board.getHeight());
    }
}
