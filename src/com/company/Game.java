package com.company;

import com.company.Powerups.PowerUpFactory;

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

    /*

     */

    private AbstractPlayer player1, player2;

    private JFrame frame;
    private Queue<List> explosionsToRemove = new ArrayDeque<>();
    private CardLayout card;
    private Board board;
    private Timer loopTimer, roundTimer;
    private GraphicsComponent gc;
    private List<Bomb> bombs = new ArrayList<>();
    private List<Point> dontRemove = new ArrayList<>(); //this is a list of pointers that indicates Tiles that should not be removed
    private List<Point> powerUp = new ArrayList<>(); //this is a list of pointers that indicates where powerups should be placed
    private boolean gameOver;
    private String winner = "it's a draw"; //initiate winner to a draw
    private static int roundTime = 60; //the specific time for each gameround
    private final static int FIRE_TIMER = 600;
    private final static int DELAY_TIME = 30;
    private final static int WINDOW_HEIGHT = 480;
    private final static int WINDOW_WIDTH = 640;


    private final Action removeExplosions = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Iterable<Point> l = new ArrayList<>(explosionsToRemove.poll());
            for (Point p : l) {
                if (!dontRemove.contains(p)) {
                    if (powerUp.contains(p)) {
                        PowerUpFactory  pUp = new PowerUpFactory();

                        board.setTile(p.x, p.y, pUp.getpUpTile().getPTILE());
                        powerUp.remove(p);
                    }
                    else {
                        board.setTile(p.x, p.y, Tiles.FLOOR);
                    }
                }
                else {
                    dontRemove.remove(p);
                }
            }
        }
    };

    public Game(final Board board) throws HeadlessException {
        player1 = Player1.getInstance();
        player2 = Player2.getInstance();
        this.board = board;
        gameOver = false;
        gc = new GraphicsComponent(board, player1, player2);



        // frametimer that
        final Action doOneStep = new AbstractAction()
        {
            @Override public void actionPerformed(ActionEvent actionEvent) {

                gameLoop();
                if (gameOver) {
                    loopTimer.stop();
                    if(player1.score > player2.score) {
                        winner = "player1";
                    } else if (player1.score < player2.score){
                        winner = "player2";
                    }
                    gc.drawWinner(winner);
                }
            }
        };


        loopTimer = new Timer(DELAY_TIME, doOneStep);
        loopTimer.setInitialDelay(0);
        loopTimer.setCoalesce(true);


        // gametime countdown each second
        final Action countDownTime = new AbstractAction()
        {
            @Override public void actionPerformed(ActionEvent actionEvent) {
                //roundTime is used in GraphicsComponent which requiers it to be static
                decreaseRoundTime();
                if (roundTime == 0) {
                    gameOver = true;
                    restartGame();
                }
            }
        };

        roundTimer = new Timer(1000, countDownTime);
        roundTimer.setInitialDelay(0);
        roundTimer.setCoalesce(true);

        setUpFrame();
    }

    // skapar spelramen
    private void setUpFrame() {
        frame = new JFrame("Bomberman");
        final JPanel game = new JPanel();
        final JPanel start = new JPanel();
        card = new CardLayout();
        final JButton play = new JButton("Play");
        final JButton quit = new JButton("Quit");

        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Not a magic constant
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

        play.addActionListener(new ActionListener()
        {
            @Override public void actionPerformed(ActionEvent actionEvent) {
                card.show(frame.getContentPane(), "game");
                roundTimer.start();
                loopTimer.start();
                //frame.pack();
                frame.requestFocus();
            }
        });
        quit.addActionListener(new ActionListener()
        {
            @Override public void actionPerformed(final ActionEvent e) {
                System.exit(0);
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
            player1.keyReleased();
        }
        if (player2.controls.contains(key)) {
            player2.keyReleased();
        }
    }

    private void gameLoop() {
        movePlayers();
        checkBombs();
        checkDamage();
        gc.repaint();
    }


    private void movePlayers() {
        makeMove(player1);
        makeMove(player2);
    }

    private void restartGame(){

    }


    private void checkDamage() {
        Point p1 = player1.position;
        if (board.getTile(p1.x, p1.y) == Tiles.FIRE) {
            if (!player1.damaged){
                player2.increaseScore();
                player1.damaged = true;
            }
        } else {
            player1.damaged = false;
        }

        Point p2 = player2.position;
        if (board.getTile(p2.x, p2.y) == Tiles.FIRE) {
            if(!player2.damaged){
                player1.increaseScore();
                player2.damaged = true;
            }
        } else {
            player2.damaged = false;
        }
    }

    public void checkBombs() {
        Iterable<Bomb> l = new ArrayList<>(bombs);
        for (Bomb b : l) {
            if (b.isExplode()) {
                // spränger bomben
                blowBomb(b);
            }
        }
    }

    // This is a big function, but it is required to check every adjacent tile
    private void blowBomb(Bomb b) {
        Tiles tile;
        bombs.remove(b);
        if (b.getPlayer().equals("Player 1")){
            player1.decreaseBombcount();
        }
        else if (b.getPlayer().equals("Player 2")){
            player2.decreaseBombcount();
        }


        List<Point> explosionPattern = new ArrayList<>();
        explosionPattern.add(new Point(b.getxPos(), b.getyPos()));
        //board.setTile(b.getxPos(), b.getyPos(), Tiles.FIRE);

        // lägger in FIRE-Tiles på alla platser där bomben exploderar
        explode_south: for (int i = 1; i <= b.getPower(); ++i) {
            tile = board.getTile(b.getxPos(), b.getyPos() + i);
            if (tile == Tiles.WALL) break;
            //this switch checks all nessesary cases
            switch (tile) {

                // om vi spränger en låda så ska loopen brytas och lådan försvinner
                case BOX:
                    powerUp.add(new Point(b.getxPos(), b.getyPos() + i));
                    explosionPattern.add(new Point(b.getxPos(), b.getyPos() + i));

                    //because we have a switch in a for loop we can not use break to break the for loop
                    break explode_south;

                // om vi spränger en bomb så ska den explodera
                case BOMB:
                    explosionPattern.add(new Point(b.getxPos(), b.getyPos() + i));
                    for (Bomb bomb : bombs) {
                        if (bomb.getxPos() == b.getxPos() && bomb.getyPos() == b.getyPos() + i) {
                            bomb.blow();
                            break;
                        }
                    }
                    break;
                    // om det redan ligger en FIRE-Tile så ska vi lägga till den i undantagsllistan
                case FIRE:
                    explosionPattern.add(new Point(b.getxPos(), b.getyPos() + i));
                    Point p = new Point(b.getxPos(), b.getyPos() + i);
                    dontRemove.add(p);
                    break;
                default:
                    explosionPattern.add(new Point(b.getxPos(), b.getyPos() + i));
                    break;
            }

        }

        // North
        explode_north: for (int i = 1; i <= b.getPower(); ++i) {
            tile = board.getTile(b.getxPos(), b.getyPos() - i);
            if (tile == Tiles.WALL) break;
            switch (tile) {

                case BOX:
                    explosionPattern.add(new Point(b.getxPos(), b.getyPos() - i));
                    powerUp.add(new Point(b.getxPos(), b.getyPos() - i));
                    break explode_north;
                case BOMB:
                    explosionPattern.add(new Point(b.getxPos(), b.getyPos() - i));
                    for (Bomb bomb : bombs) {
                        if (bomb.getxPos() == b.getxPos() && bomb.getyPos() == b.getyPos() - i) {
                            bomb.blow();
                            break;
                        }
                    }
                    break;
                case FIRE:
                    explosionPattern.add(new Point(b.getxPos(), b.getyPos() - i));
                    Point p = new Point(b.getxPos(), b.getyPos() - i);
                    dontRemove.add(p);
                    break;
                default:
                    explosionPattern.add(new Point(b.getxPos(), b.getyPos() - i));
                    break;
            }
        }
        // east
        explode_east: for (int i = 1; i <= b.getPower(); ++i) {
            tile = board.getTile(b.getxPos() + i, b.getyPos());
            if (tile == Tiles.WALL) break;
            switch (tile) {

                case BOX:
                    explosionPattern.add(new Point(b.getxPos() + i, b.getyPos()));
                    powerUp.add(new Point(b.getxPos() + i, b.getyPos()));
                    break explode_east;
                case BOMB:
                    explosionPattern.add(new Point(b.getxPos() + i, b.getyPos()));
                    for (Bomb bomb : bombs) {
                        if (bomb.getxPos() == b.getxPos() + i && bomb.getyPos() == b.getyPos()) {
                            bomb.blow();
                            break;
                        }
                    }
                    break;
                case FIRE:
                    explosionPattern.add(new Point(b.getxPos() + i, b.getyPos()));
                    Point p = new Point(b.getxPos() + i, b.getyPos());
                    dontRemove.add(p);
                    break;
                default:
                    explosionPattern.add(new Point(b.getxPos() + i, b.getyPos()));
                    break;
            }
        }

        // West
        explode_west: for (int i = 1; i <= b.getPower(); ++i) {
            tile = board.getTile(b.getxPos() - i, b.getyPos());
            if (tile == Tiles.WALL) break;
            switch (tile) {
                case BOX:
                    powerUp.add(new Point(b.getxPos() - i, b.getyPos()));
                    explosionPattern.add(new Point(b.getxPos() - i, b.getyPos()));
                    break explode_west;
                case BOMB:
                    explosionPattern.add(new Point(b.getxPos() - i, b.getyPos()));
                    for (Bomb bomb : bombs) {
                        if (bomb.getxPos() == b.getxPos() - i && bomb.getyPos() == b.getyPos()) {
                            bomb.blow();
                            break;
                        }
                    }
                    break;
                case FIRE:
                    explosionPattern.add(new Point(b.getxPos() - i, b.getyPos()));
                    Point p = new Point(b.getxPos() - i, b.getyPos());
                    dontRemove.add(p);
                    break;
                default:
                    explosionPattern.add(new Point(b.getxPos() - i, b.getyPos()));
                    break;
            }
        }

        // går igenom listan på alla positioner som ska exploderas och
        // sätter en FIRE-Tile på dessa positioner
        for (Point p : explosionPattern) {
            board.setTile(p.x, p.y, Tiles.FIRE);
        }

        // lägger till de postioner som vi exploderade och startar en Timer
        // som gör att explosionerna tas bort efter en viss DELAY
        explosionsToRemove.add(explosionPattern);
        Timer t = new Timer(FIRE_TIMER, removeExplosions);
        t.start();
        t.setRepeats(false);
    }


    public void destroyPUP(int xPos, int yPos){
        board.setTile(xPos,yPos,Tiles.FLOOR);
    }

    // hanterar den PlayerAction som spelaren vill utföra
    private void makeMove(AbstractPlayer p) {
        // kollar om spelaren vill placera ut en bomb
        if (p.isDroppingBomb() && p.bombs > p.bombcount) {
            if (board.getTile(p.position.x, p.position.y) == Tiles.FLOOR) {
                Bomb b = new Bomb(p);
                bombs.add(b);
                board.setTile(b.getxPos(), b.getyPos(), Tiles.BOMB);
                p.setDroppingBomb(false);
                p.increaseBombcount();
            }
        }

        // annars vill spelaren röra på sig i någon riktining
        else {
            if (p.getAction() != PlayerAction.PLAYER_STAND) {
                Point initialPosition =  new Point(p.position);
                AbstractPlayer otherPlayer;
                //Player otherPlayer;

                if (p.equals(player1)) {
                    otherPlayer = player2;
                }
                else {
                    otherPlayer = player1;
                }

                p.movePlayer();

                Tiles nextTile = (board.getTile(p.position.x, p.position.y));

                // kollar om rutan spelaren vill röra sig till är ledig
                // om inte den är det så ändras inte spelarens position
                if (nextTile == Tiles.PBOMB) {
                    p.increaseBombs();
                    destroyPUP(p.position.x, p.position.y);
                } else if (nextTile == Tiles.PFIRE) {
                    p.increasePower();
                    destroyPUP(p.position.x, p.position.y);
                } else if (nextTile == Tiles.PSPEED) {
                    p.increaseSpeed();
                    destroyPUP(p.position.x, p.position.y);
                } else if (nextTile != Tiles.FLOOR
                        || p.position.equals(otherPlayer.position)) {
                    p.position.setLocation(initialPosition);
                }
            }
        }
    }

    public static int getRoundTime() {
        return roundTime;
    }

    public void decreaseRoundTime(){
        //noinspection AssignmentToStaticFieldFromInstanceMethod
        roundTime -= 1;
    }
}
