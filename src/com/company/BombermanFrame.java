package com.company;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Håkan on 2015-07-31.
 */
public class BombermanFrame extends JFrame{
    private JFrame frame;
    private GraphicsComponent gc;

    public BombermanFrame(final Board board) throws HeadlessException {
        this.frame = new JFrame("Bomberman");
        this.gc = new GraphicsComponent(board);
        frame.add(gc);
        frame.pack();
        frame.setVisible(true);
    }
}
