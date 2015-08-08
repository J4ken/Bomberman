package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Håkan on 2015-07-31.
 */
public class Game extends JFrame implements KeyListener{
    private JFrame frame;
    private JPanel game, start;
    private JButton play, quit;
    private CardLayout card;
    private Board board;
    private List<Bomb> bombs = new ArrayList<Bomb>();
    private Player player1, player2;
    private GraphicsComponent gc;
    private final static int WINDOW_HEIGHT = 480;
    private final static int WINDOW_WIDTH = 640;

    public Game(final Board board) throws HeadlessException {
        this.board = board;
        player1 = new Player(1);
        player2 = new Player(2);
        gc = new GraphicsComponent(board, player1, player2);

        setUpFrame();
    }

    // skapar spelramen
    private void setUpFrame() {
        frame = new JFrame("Bomberman");
        game = new JPanel();
        start = new JPanel();
        card = new CardLayout();
        play = new JButton("Play");
        quit = new JButton("Quit");

        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(card);
        frame.addKeyListener(this);

        start.setBackground(Color.BLACK);
        start.add(play);
        start.add(quit);
        start.setVisible(true);
        frame.getContentPane().add(start, "start");

        game.add(gc);
        game.setVisible(false);
        frame.getContentPane().add(game, "game");

        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                card.show(frame.getContentPane(), "game");
                frame.requestFocus();
            }
        });
        System.out.println(player1.position.x);
        System.out.println(player1.position.y);
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (player1.controls.contains(key)) {
            player1.keyPressed(e);
            makeMove(player1);
        }
        if (player2.controls.contains(key)) {
            player2.keyPressed(e);
            makeMove(player2);
        }
        gc.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (player1.controls.contains(key)) {
            player1.keyReleased(e);
        }
        if (player2.controls.contains(key)) {
            player2.keyReleased(e);
        }
    }

    public void checkBombs() {
        for (Bomb b : bombs) {
            if (b.isExplode()) {
                // spränger bomben
                bombs.remove(b);
            }
        }
    }


    // hanterar den Action som spelaren vill utföra
    private void makeMove(Player p) {
        if (p.getAction() != Action.STAND) {

            // kollar om spelaren vill placera ut en bomb
            if (p.getAction() == Action.BOMB) {
                Bomb b = new Bomb(p);
                bombs.add(b);
                board.setTile(b.getxPos(), b.getyPos(), Tiles.BOMB);
            }

            // annars vill spelaren röra på sig i någon riktining
            else {
                Point initialPosition =  new Point(p.position);
                Player otherPlayer;

                if (p == player1) {
                    otherPlayer = player2;
                }
                else {
                    otherPlayer = player1;
                }

                p.movePlayer();

                // kollar om rutan spelaren vill röra sig till är ledig
                // om inte den är det så ändras inte spelarens position
                if (board.getTile(p.position.x, p.position.y) != Tiles.FLOOR
                        || p.position.equals(otherPlayer.position)) {
                    p.position.setLocation(initialPosition);
                }
            }
        }
    }
}
