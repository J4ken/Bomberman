package com.company;

import java.util.Random;

/**
 * Created by Jacob on 2015-08-14.
 *
 * Creates a random Powerup Tile where the box where destryed
 */
public class PowerUP {

    public int pUP, xPos, yPos;
    public Tiles pUpTile;

    public PowerUP(){
        Random rng = new Random();
        int pUP = rng.nextInt(8);
        switch (pUP) {
            case 0:
                pUpTile = Tiles.PBOMB;
                break;
            case 1:
                //pUpTile = Tiles.PSPEED;
                pUpTile = Tiles.FLOOR;
                break;
            case 2:
                pUpTile = Tiles.PPOWER;
                break;
            default:
                pUpTile = Tiles.FLOOR;
        }
    }

    public Tiles getPUP(){
        return pUpTile;
    }


}
