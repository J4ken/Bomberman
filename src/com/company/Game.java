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
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        player2.keyPressed(e);
        player1.keyPressed(e);
        if (player1.getAction() == Action.BOMB) {
            Bomb bomb = new Bomb(player1);
            bombs.add(bomb);
            board.setTile(bomb.getxPos(), bomb.getyPos(), Tiles.BOMB);
        }
        if (canMove(player2)){
            player2.move();
            player2.setAction(Action.STAND);
        }
        if (canMove(player1)) {
            player1.move();
            player1.setAction(Action.STAND);
        }
        gc.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        player1.keyReleased(e);
        player2.keyReleased(e);
    }

    private boolean canMove(Player p) {
        if (p.getAction() != Action.STAND) {
            int otherPlayerxPos;
            int otherPlayeryPos;
            if (p == player1) {
                otherPlayerxPos = player2.getxPos();
                otherPlayeryPos = player2.getyPos();
            }
            else {
                otherPlayerxPos = player1.getxPos();
                otherPlayeryPos = player1.getyPos();
            }
            switch (p.getAction()) {
                case UP:
                    if (board.getTile(p.getyPos()- 1, p.getxPos()) == Tiles.FLOOR
                            && (p.getyPos() - 1 != otherPlayeryPos && p.getxPos() != otherPlayerxPos)) {
                        return true;
                    }
                    return false;
                case DOWN:
                    if (board.getTile(p.getyPos() + 1, p.getxPos()) == Tiles.FLOOR) {
                        return true;
                    }
                    return false;
                case RIGHT:
                    if (board.getTile(p.getyPos(), p.getxPos() + 1) == Tiles.FLOOR) {
                        return true;
                    }
                    return false;
                case LEFT:
                    if (board.getTile(p.getyPos(), p.getxPos()- 1) == Tiles.FLOOR) {
                        return true;
                    }
                    return false;
                default: break;

            }
        }
        return false;
    }
}
