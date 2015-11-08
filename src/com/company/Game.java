package com.company;

import com.company.powerups.PowerUpFactory;

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
    private Queue<List<Point>> explosionsToRemove = new ArrayDeque<>(); //this is a Queue of List of explosions from a bomb
    private CardLayout card;
    private Board board;
    private Timer loopTimer, roundTimer;
    private GraphicsComponent gc;
    private List<Bomb> bombs = new ArrayList<>();
    private List<Point> dontRemove = new ArrayList<>(); //this is a list of pointers that indicates Tiles that should not be removed
    private List<Point> powerUp = new ArrayList<>(); //this is a list of pointers that indicates where powerups should be placed
    private boolean gameOver;
    private String winner = "no one! it's a draw"; //initiate winner to a draw
    private static int roundTime = 60; //the specific time for each gameround
    private final static int FIRE_TIMER = 600;
    private final static int DELAY_TIME = 30;
    private final static int WINDOW_HEIGHT = 480;
    private final static int WINDOW_WIDTH = 640;
    private PowerUpFactory pUp = new PowerUpFactory();


    private final Action removeExplosions = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Iterable<Point> explosions = new ArrayList<>(explosionsToRemove.poll()); //this is correct
            for (Point point : explosions) {
                if (!dontRemove.contains(point)) {
                    if (powerUp.contains(point)) {
                        board.setTile(point.x, point.y, pUp.getRandomPowerUp().getPTILE());
                        powerUp.remove(point);
                    }
                    else {
                        board.setTile(point.x, point.y, Tiles.FLOOR);
                    }
                }
                else {
                    dontRemove.remove(point);
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

    private void checkDamage    () {
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
        Iterable<Bomb> bombs = new ArrayList<>(this.bombs);
        for (Bomb bomb : bombs) {
            if (bomb.isExplode()) {
                // spränger bomben
                blowBomb(bomb);
            }
        }
    }
    private void explodeFunction(String direction, Bomb b) {

        int dirX = 0;
        int dirY = 0;
        int i = 1;
        int power = b.getPower();
        List<Point> explosionPattern = new ArrayList<>();
        explosionPattern.add(new Point(b.getxPos(), b.getyPos()));

        while (i <= power){

	    switch(direction){
                case "east":
                    dirX++;
                    break;
                case "west":
                    dirX--;
                    break;
                case "north":
                    dirY--;
                    break;
                case "south":
                    dirY++;
                    break;
            }

            Point currentPoint = new Point(b.getxPos() + dirX, b.getyPos() + dirY);
            Tiles tile = board.getTile(b.getxPos() + dirX, b.getyPos() + dirY);
            if (tile == Tiles.WALL) break;

            switch (tile) {
                case BOX:
                    powerUp.add(currentPoint);
                    explosionPattern.add(currentPoint);
                    break;
                case BOMB:
                    explosionPattern.add(currentPoint);
                    for (Bomb bomb : bombs) {
                        if (bomb.getxPos() == b.getxPos() + dirX && bomb.getyPos() == b.getyPos() + dirY) {
                            bomb.blow();
                            break;
                        }
                    }
                    break;
                case FIRE:
                    explosionPattern.add(currentPoint);
                    dontRemove.add(currentPoint);
                    break;
                default:
                    explosionPattern.add(currentPoint);
                    break;
            }
            i++;
        }
	for (Point p : explosionPattern) {
	     board.setTile(p.x, p.y, Tiles.FIRE);
	 }

	 explosionsToRemove.add(explosionPattern);
	 Timer t = new Timer(FIRE_TIMER, removeExplosions);
	 t.start();
	 t.setRepeats(false);
    }

    private void blowBomb(Bomb b){
        bombs.remove(b);

        //decrese player bomb
        if (b.getPlayer().equals(player1.getName())){
            player1.decreaseBombcount();
        }
        else if (b.getPlayer().equals(player2.getName())){
            player2.decreaseBombcount();
        }
        explodeFunction("east", b);
        explodeFunction("west", b);
        explodeFunction("north", b);
        explodeFunction("south", b);
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

    //roundTime is called by GraphicsComponent which requers it to be static
    //and we have to decrease it somehow
    public void decreaseRoundTime(){
        roundTime -= 1;
    }
}
