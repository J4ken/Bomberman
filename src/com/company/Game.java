package com.company;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
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
    private Timer loopTimer;
    private Set<Point> dontRemove = new HashSet<Point>();
    private Map<Point, Integer> explosionCounter = new HashMap<Point, Integer>();
    private List<Bomb> bombs = new ArrayList<Bomb>();
    private Player player1, player2;
    private GraphicsComponent gc;
    private final static int BLOW_DIRECTIONS = 4;
    private final static int WINDOW_HEIGHT = 480;
    private final static int WINDOW_WIDTH = 640;

    public Game(final Board board) throws HeadlessException {
        this.board = board;
        player1 = new Player(1);
        player2 = new Player(2);
        gc = new GraphicsComponent(board, player1, player2);
        final Action doOneStep = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                gameLoop();
            }
        };

        loopTimer = new Timer(250, doOneStep);
        loopTimer.setInitialDelay(0);
        loopTimer.setCoalesce(true);
        loopTimer.start();

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

    private void gameLoop() {
        checkBombs();
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
        List<Bomb> l = new ArrayList<Bomb>(bombs);
        for (Bomb b : l) {
            if (b.isExplode()) {
                // spränger bomben
                bombs.remove(b);
                blowBomb(b);
            }
        }
    }

    private void blowBomb(Bomb b) {
        Tiles t;
        board.setTile(b.getxPos(), b.getyPos(), Tiles.FIRE);

        // lägger in FIRE-Tiles på alla platser där bomben exploderar
        for (int i = 0; i < b.getPower(); ++i) {

            t = board.getTile(b.getxPos(), b.getyPos() + i);
            switch (t) {

                // ersätter FLOOR med FIRE
                case FLOOR:
                    board.setTile(b.getxPos(), b.getyPos() + i, Tiles.FIRE);
                    break;

                // om vi spränger en låda så ska loopen brytas och lådan försvinner
                case BOX:
                    board.setTile(b.getxPos(), b.getyPos() + i, Tiles.FIRE);
                    // fixa så att en powerup kommer fram
                    i = b.getPower();
                    break;

                // om vi spränger en bomb så ska den explodera
                case BOMB:
                    for (Bomb bomb : bombs) {
                        if (bomb.getxPos() == b.getxPos() && bomb.getyPos() == b.getyPos() + i) {
                            bomb.blow();
                            break;
                        }
                    }

                // om det redan ligger en FIRE-Tile så ska vi lägga till den i undantagsllistan
                case FIRE:
                    Point p = new Point(b.getxPos(), b.getyPos());
                    dontRemove.add(p);
            }
        }

        for (int i = 0; i < b.getPower(); ++i) {
            if (i == 0) continue;
            t = board.getTile(b.getxPos(), b.getyPos() - i);
            switch (t) {
                case FLOOR:
                    board.setTile(b.getxPos(), b.getyPos() - i, Tiles.FIRE);
                    break;
                case BOX:
                    board.setTile(b.getxPos(), b.getyPos() - i, Tiles.FIRE);
                    // fixa så att en powerup kommer fram
                    i = b.getPower();
                    break;
                case BOMB:
                    for (Bomb bomb : bombs) {
                        if (bomb.getxPos() == b.getxPos() && bomb.getyPos() == b.getyPos() - i) {
                            bomb.blow();
                            break;
                        }
                    }
                case FIRE:
                    Point p = new Point(b.getxPos(), b.getyPos() - i);
                    dontRemove.add(p);
            }
        }

        for (int i = 0; i < b.getPower(); ++i) {
            if (i == 0) continue;
            t = board.getTile(b.getxPos() + i, b.getyPos());
            switch (t) {
                case FLOOR:
                    board.setTile(b.getxPos() + i, b.getyPos(), Tiles.FIRE);
                    break;
                case BOX:
                    board.setTile(b.getxPos() + i, b.getyPos(), Tiles.FIRE);
                    // fixa så att en powerup kommer fram
                    i = b.getPower();
                    break;
                case BOMB:
                    for (Bomb bomb : bombs) {
                        if (bomb.getxPos() == b.getxPos() + i && bomb.getyPos() == b.getyPos() + i) {
                            bomb.blow();
                            break;
                        }
                    }
                case FIRE:
                    Point p = new Point(b.getxPos() + i, b.getyPos());
                    dontRemove.add(p);
            }
        }

        for (int i = 0; i < b.getPower(); ++i) {
            if (i == 0) continue;
            t = board.getTile(b.getxPos() - i, b.getyPos());
            switch (t) {
                case FLOOR:
                    board.setTile(b.getxPos() - i, b.getyPos(), Tiles.FIRE);
                    break;
                case BOX:
                    board.setTile(b.getxPos() - i, b.getyPos(), Tiles.FIRE);
                    // fixa så att en powerup kommer fram
                    i = b.getPower();
                    break;
                case BOMB:
                    for (Bomb bomb : bombs) {
                        if (bomb.getxPos() == b.getxPos() - i && bomb.getyPos() == b.getyPos() + i) {
                            bomb.blow();
                            break;
                        }
                    }
                case FIRE:
                    Point p = new Point(b.getxPos() - i, b.getyPos());
                    dontRemove.add(p);
            }
        }
    }

    // hanterar den PlayerAction som spelaren vill utföra
    private void makeMove(Player p) {
        if (p.getAction() != PlayerAction.STAND) {

            // kollar om spelaren vill placera ut en bomb
            if (p.getAction() == PlayerAction.BOMB) {
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
