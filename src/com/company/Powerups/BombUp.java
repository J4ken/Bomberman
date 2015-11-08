package com.company.powerups;

import com.company.Tiles;
/**
 * Created by Jacob on 10/23/2015.
 */
public class BombUp implements PowerUp
{

    @Override public Tiles getPTILE() {
	return Tiles.PBOMB;
    }
}
