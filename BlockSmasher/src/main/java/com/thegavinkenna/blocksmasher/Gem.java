package com.thegavinkenna.blocksmasher;

import android.graphics.Bitmap;

/**
 * Created by Gavin on 25/06/13.
 */
public abstract class Gem extends Breakable {

    private boolean fallingGem; //Does this gem fall when shown?
    private float timeLasts; //How long does this powerup/gem last?
    private boolean captured; //Player took it?

    public Gem(Bitmap bitmap, int x, int y, int col,int currentHealth, int pointsToGive, float time){

        super(1,pointsToGive,bitmap, x, y, col);
        this.timeLasts = time;
        this.captured = false;
    }

    public boolean IsAFallingGem(){
        return this.fallingGem;
    }

    public void SetFalling(boolean f){
        this.fallingGem = f;
    }

    public void update(){

    }

    public abstract void Captured(); //After the player hits it
    public abstract void TimeUp(); //After time has expired

}
