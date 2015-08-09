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
import java.util.concurrent.DelayQueue;


/**
 * Created by Håkan on 2015-07-31.
 */
public class Game extends JFrame implements KeyListener{
    private JFrame frame;
    private Queue<List> explosionsToRemove = new ArrayDeque<List>();
    private JPanel game, start;
    private JButton play, quit;
    private CardLayout card;
    private Board board;
    private Timer loopTimer;
    private Map<Point, Integer> explosionCounter = new HashMap<Point, Integer>();
    private List<Point> explosionPattern;
    private Player player1, player2;
    private GraphicsComponent gc;
    private PriorityQueue<List<Point>> expslosionsToRemove = new PriorityQueue();
    private final static int WINDOW_HEIGHT = 480;
    private final static int WINDOW_WIDTH = 640;

    public static List<Bomb> bombs = new ArrayList<Bomb>();
    public static Set<Point> dontRemove = new HashSet<Point>();

    final Action removeExplosions = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            System.out.printf("REMOVING");
            List<Point> l = new ArrayList<Point>(explosionsToRemove.poll());
            for (Point p : l) {
                if (!dontRemove.contains(p)) {
                    board.setTile(p.x, p.y, Tiles.FLOOR);
                }
                else {
                    dontRemove.remove(p);
                }
            }
        }
    };

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



        loopTimer = new Timer(30, doOneStep);
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
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (player1.controls.contains(key)) {
            player1.keyPressed(e);
        }
        if (player2.controls.contains(key)) {
            player2.keyPressed(e);
        }
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

    private void gameLoop() {
        makeMove(player1);
        makeMove(player2);
        checkBombs();
        gc.repaint();
    }

    public void checkBombs() {
        List<Bomb> l = new ArrayList<Bomb>(bombs);
        for (Bomb b : l) {
            if (b.isExplode()) {
                // spränger bomben
                blowBomb(b);
            }
        }
    }

    private void blowBomb(Bomb b) {
        Tiles tile;
        bombs.remove(b);
        List<Point> explosionPattern = new ArrayList<Point>();
        explosionPattern.add(new Point(b.getxPos(), b.getyPos()));
        //board.setTile(b.getxPos(), b.getyPos(), Tiles.FIRE);

        // lägger in FIRE-Tiles på alla platser där bomben exploderar
        for (int i = 0; i < b.getPower(); ++i) {
            if (i == 0) continue;
            tile = board.getTile(b.getxPos(), b.getyPos() + i);
            if (tile != Tiles.WALL) {
                explosionPattern.add(new Point(b.getxPos(), b.getyPos() + i));
                switch (tile) {

                    // ersätter FLOOR med FIRE
                    case FLOOR:
                        //        board.setTile(b.getxPos(), b.getyPos() + i, Tiles.FIRE);
                        break;

                    // om vi spränger en låda så ska loopen brytas och lådan försvinner
                    case BOX:
                        //      board.setTile(b.getxPos(), b.getyPos() + i, Tiles.FIRE);
                        // fixa så att en powerup kommer fram
                        i = b.getPower();
                        break;

                    // om vi spränger en bomb så ska den explodera
                    case BOMB:
                        //    board.setTile(b.getxPos(), b.getyPos() + i, Tiles.FIRE);
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
        }

        for (int i = 0; i < b.getPower(); ++i) {
            if (i == 0) continue;
            tile = board.getTile(b.getxPos(), b.getyPos() - i);
            if (tile != Tiles.WALL) {
                explosionPattern.add(new Point(b.getxPos(), b.getyPos() - i));
                switch (tile) {
                    case FLOOR:
                        //        board.setTile(b.getxPos(), b.getyPos() - i, Tiles.FIRE);
                        break;
                    case BOX:
                        //      board.setTile(b.getxPos(), b.getyPos() - i, Tiles.FIRE);
                        // fixa så att en powerup kommer fram
                        i = b.getPower();
                        break;
                    case BOMB:
                        //    board.setTile(b.getxPos(), b.getyPos() - i, Tiles.FIRE);
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
        }

        for (int i = 0; i < b.getPower(); ++i) {
            if (i == 0) continue;
            tile = board.getTile(b.getxPos() + i, b.getyPos());
            if (tile != Tiles.WALL) {
                explosionPattern.add(new Point(b.getxPos() + i, b.getyPos()));
                switch (tile) {
                    case FLOOR:
                        //        board.setTile(b.getxPos() + i, b.getyPos(), Tiles.FIRE);
                        break;
                    case BOX:
                        //      board.setTile(b.getxPos() + i, b.getyPos(), Tiles.FIRE);
                        // fixa så att en powerup kommer fram
                        i = b.getPower();
                        break;
                    case BOMB:
                        //    board.setTile(b.getxPos() + i, b.getyPos(), Tiles.FIRE);
                        for (Bomb bomb : bombs) {
                            if (bomb.getxPos() == b.getxPos() + i && bomb.getyPos() == b.getyPos()) {
                                bomb.blow();
                                break;
                            }
                        }
                    case FIRE:
                        Point p = new Point(b.getxPos() + i, b.getyPos());
                        dontRemove.add(p);
                }
            }
        }

        for (int i = 0; i < b.getPower(); ++i) {
            if (i == 0) continue;
            tile = board.getTile(b.getxPos() - i, b.getyPos());
            if (tile != Tiles.WALL) {
                explosionPattern.add(new Point(b.getxPos() - i, b.getyPos()));
                switch (tile) {
                    case FLOOR:
//                    board.setTile(b.getxPos() - i, b.getyPos(), Tiles.FIRE);
                        break;
                    case BOX:
                        //                  board.setTile(b.getxPos() - i, b.getyPos(), Tiles.FIRE);
                        // fixa så att en powerup kommer fram
                        i = b.getPower();
                        break;
                    case BOMB:
                        //                board.setTile(b.getxPos() - i, b.getyPos(), Tiles.FIRE);
                        for (Bomb bomb : bombs) {
                            if (bomb.getxPos() == b.getxPos() - i && bomb.getyPos() == b.getyPos()) {
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

        List<Point> explosionPatternCopy = new ArrayList<Point>(explosionPattern);
        for (Point p : explosionPatternCopy) {
            board.setTile(p.x, p.y, Tiles.FIRE);
        }
        explosionsToRemove.add(explosionPatternCopy);
        Timer t = new Timer(600, removeExplosions);
        t.start();
        t.setRepeats(false);
/*
        for (Point p : explosionPatternCopy) {
            board.setTile(p.x, p.y, Tiles.FLOOR);
        }*/
    }

    private void removeExplosions(List l) {

    }

    // hanterar den PlayerAction som spelaren vill utföra
    private void makeMove(Player p) {
        // kollar om spelaren vill placera ut en bomb
        if (p.isDroppingBomb()) {
            if (board.getTile(p.position.x, p.position.y) == Tiles.FLOOR) {
                System.out.printf("placerar bomb");
                Bomb b = new Bomb(p);
                bombs.add(b);
                board.setTile(b.getxPos(), b.getyPos(), Tiles.BOMB);
                p.setDroppingBomb(false);
            }
        }

        // annars vill spelaren röra på sig i någon riktining
        else {
            if (p.getAction() != PlayerAction.STAND) {
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
