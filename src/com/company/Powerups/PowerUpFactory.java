package com.company.Powerups;

import com.company.Tiles;

import java.util.Random;

/**
 * Created by Jacob on 2015-08-14.
 *
 * Creates a random Powerup Tile where the box where destryed
 */
public class PowerUpFactory
{
    public PowerUp getpUpTile(){

        Random rng = new Random();
        int pUP = rng.nextInt(8);
        switch (pUP) {
            case 0:
                return new FireUp();
            case 1:
                return new BombUp();
            default:
                return new NoPowerup();
        }
    }
}
