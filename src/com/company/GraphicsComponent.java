package com.company;

import javafx.scene.paint.*;
import sun.plugin2.util.ColorUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.AbstractMap;
import java.util.EnumMap;

/**
 * Created by H�kan on 2015-07-31.
 */
public class GraphicsComponent extends JComponent implements KeyListener{
    private Board board;
    private Player player1, player2;
    private AbstractMap<Tiles, Color> enumMap;
    final static int TILE_SIZE = 30;

    public GraphicsComponent(Board board) {
        this.board = board;
        this.player1 = new Player(1);
        this.player2 = new Player(2);
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

        System.out.print(player1.getxPos());

        g2d.setColor(enumMap.get(Tiles.PLAYER1));
        g2d.fillRect(player1.getxPos(), player1.getyPos(), TILE_SIZE, TILE_SIZE);

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(TILE_SIZE * board.getWidth(), TILE_SIZE * board.getHeight());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        player1.keyReleased(e);
        player1.move();
        player2.keyReleased(e);
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        player1.keyPressed(e);
        player1.move();
        player2.keyPressed(e);
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }
}
