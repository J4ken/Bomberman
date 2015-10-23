package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.AbstractMap;
import java.util.EnumMap;

/**
 * Created by Håkan on 2015-07-31.
 */
public class GraphicsComponent extends JComponent {


    /**
     * Theese varables are self explaining
     */
    public static final int FONT_SIZE = 20; //
    public static final int PLAYER1_XPOSITION = 30; //
    public static final int NAMEBAR_YPOSITION = 30;
    public static final int SCORE_YPOSITION = 50;
    public static final int PLAYER2_XPOSITION = 300;
    public static final int GAMETIME_XPOSITON = 180;
    /**
     * GraphicsComponent manages the graphics in Bomberman.
     * Each Tile is paired with a color in a EnumMap.
     */

    private Board board;
    private AbstractPlayer player1, player2;
    private AbstractMap<Tiles, Color> enumMap;

    /**
     * TILE_SIZE is the number of pixels used for each tile
     */
    public final static int TILE_SIZE = 32;


    public GraphicsComponent(Board board, AbstractPlayer player1, AbstractPlayer player2) {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
        setColorMap();
    }

    private void setColorMap() {
        enumMap = new EnumMap<>(Tiles.class);
        enumMap.put(Tiles.BOMB, Color.BLACK);
        enumMap.put(Tiles.BOX, Color.BLUE);
        enumMap.put(Tiles.FIRE, Color.ORANGE);
        enumMap.put(Tiles.WALL, Color.DARK_GRAY);
        enumMap.put(Tiles.FLOOR, Color.GREEN);
        enumMap.put(Tiles.PLAYER1, Color.CYAN);
        enumMap.put(Tiles.PLAYER2, Color.RED);
        enumMap.put(Tiles.PBOMB, Color.YELLOW);
        enumMap.put(Tiles.PFIRE, Color.PINK);
        enumMap.put(Tiles.PSPEED, Color.LIGHT_GRAY);

    }


    public void drawWinner(String winner){
        JFrame frame = new JFrame("winner");
        JOptionPane.showMessageDialog(frame, "the winner is " + winner, "WINNER", JOptionPane.PLAIN_MESSAGE);
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
        g2d.setFont(new Font("SansSerif", Font.BOLD, FONT_SIZE));

        g2d.setColor(enumMap.get(Tiles.PLAYER1));
        g2d.drawString(player1.getName() + " : " + player1.getScore(), PLAYER1_XPOSITION, NAMEBAR_YPOSITION);
        g2d.fillRect(player1.position.x * TILE_SIZE, player1.position.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        g2d.drawString("B: " + player1.bombs + "  P: " + player1.power, PLAYER1_XPOSITION, SCORE_YPOSITION);


        g2d.setColor(enumMap.get(Tiles.PLAYER2));
        g2d.drawString(player2.getName() + " : " + player2.getScore(), PLAYER2_XPOSITION, NAMEBAR_YPOSITION);
        g2d.fillRect(player2.position.x * TILE_SIZE, player2.position.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        g2d.drawString("B: " + player2.bombs + "  P: " + player2.power, PLAYER2_XPOSITION, SCORE_YPOSITION);

        g2d.setColor(Color.YELLOW);
        g2d.drawString("Time: " + Game.getRoundTime(), GAMETIME_XPOSITON, NAMEBAR_YPOSITION);
    }

    //this is not an issue!
    //we do want to Override and ignore getPreferredSize to set the size of the winidow
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(TILE_SIZE * board.getWidth(), TILE_SIZE * board.getHeight());
    }
}
