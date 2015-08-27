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
 * Created by Håkan on 2015-07-31.
 */
public class GraphicsComponent extends JComponent {
    private Board board;
    private AbstractPlayer player1, player2;
    private AbstractMap<Tiles, Color> enumMap;
    private int timer;
    public final static int TILE_SIZE = 30;


    public GraphicsComponent(Board board, AbstractPlayer player1, AbstractPlayer player2, int timer) {
        this.board = board;
        this.timer = timer;
        this.player1 = player1;
        this.player2 = player2;
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
        enumMap.put(Tiles.PBOMB, Color.YELLOW);
        enumMap.put(Tiles.PPOWER, Color.PINK);
        enumMap.put(Tiles.PSPEED, Color.LIGHT_GRAY);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        final Graphics2D g2d = (Graphics2D) g;

        for (int i = 0; i < board.getHeight(); ++i) {
            for (int j = 0; j < board.getWidth(); ++j) {
                g2d.setColor(enumMap.get(board.getTile(j, i)));
                g2d.fillRect(j * TILE_SIZE, i * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }
        g2d.setFont(new Font("SansSerif", Font.BOLD, 20));

        g2d.setColor(enumMap.get(Tiles.PLAYER1));
        g2d.drawString(player1.getName() + " : " + player1.getScore(), 30, 30);
        g2d.fillRect(player1.position.x * TILE_SIZE, player1.position.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        g2d.drawString("B: " + player1.bombs + "  P: " + player1.power, 30, 50);


        g2d.setColor(enumMap.get(Tiles.PLAYER2));
        g2d.drawString(player2.getName() + " : " + player2.getScore(), 300, 30);
        g2d.fillRect(player2.position.x * TILE_SIZE, player2.position.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        g2d.drawString("B: " + player2.bombs + "  P: " + player2.power, 300, 50);

        g2d.setColor(Color.YELLOW);
        g2d.drawString("Time: " + Game.getRoundTime(), 180, 30);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(TILE_SIZE * board.getWidth(), TILE_SIZE * board.getHeight());
    }
}
