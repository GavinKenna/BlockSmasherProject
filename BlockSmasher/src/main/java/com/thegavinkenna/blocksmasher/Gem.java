package com.thegavinkenna.blocksmasher;

import android.graphics.Bitmap;

/**
 * Created by Gavin on 25/06/13.
 */
public abstract class Gem extends Breakable {

    private boolean fallingGem; //Does this gem fall when shown?

    public Gem(Bitmap bitmap, int x, int y, int col,int currentHealth, int pointsToGive){

        super(1,pointsToGive,bitmap, x, y, col);

    }

    public boolean IsAFallingGem(){
        return this.fallingGem;
    }

    public void SetFalling(boolean f){
        this.fallingGem = f;
    }

    public abstract void Captured(); //After the player hits it

}
